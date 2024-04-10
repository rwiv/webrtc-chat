import {Account, ChatMessage, ChatUser} from "@/graphql/types.ts";
import React, {useRef, useState} from "react";
import {Client, IMessage, StompSubscription} from "@stomp/stompjs";
import { CandidateMessage, DescriptionMessage, requestAnswer, requestCandidate, requestOffer } from "@/client/signaling.ts";
import {createStompClient} from "@/lib/web/stomp.ts";
import {sendMessage} from "@/client/chatMessage.ts";
import {ScrollType} from "@/hooks/chatmessage/useChatMessagesScroll.ts";

export function useChatMessagesRTC(
  chatRoomId: number,
  myInfo: Account,
  chatUsers: ChatUser[],
  setChatMessages: React.Dispatch<React.SetStateAction<ChatMessage[]>>,
  setOffset: React.Dispatch<React.SetStateAction<number>>,
  setScrollType: React.Dispatch<React.SetStateAction<ScrollType>>,
) {

  const dccs = useRef<Map<number, DataChannelConnection>>(new Map());
  const [stompClient, setStompClient] = useState<Client>();
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);
  const [loading, setLoading] = useState(true);

  const isLoading = () => {
    for (const dcc of dccs.current.values()) {
      if (dcc.getOpenChannel() !== null) return false;
    }
    return true;
  }

  const onMessage = (ev: MessageEvent) => {
    const chatMessage = JSON.parse(ev.data) as ChatMessage;
    setChatMessages(prev => [...prev, chatMessage]);
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");
  }

  const createPeerConnection = async (target: Account) => {
    const pc = new RTCPeerConnection({
      iceServers: [ { urls: "stun:stun.l.google.com:19302" } ],
    });
    pc.onicecandidate = async ev => {
      if (ev.candidate === undefined || ev.candidate === null) return;

      await requestCandidate(chatRoomId, {
        candidate: ev.candidate,
        senderId: myInfo.id,
        receiverId: target.id,
      });
    }

    const myChannel = pc.createDataChannel(target.id.toString());
    myChannel.onmessage = onMessage;

    pc.ondatachannel = async ev => {
      const yourChannel = ev.channel;
      yourChannel.onmessage = onMessage;

      const dcc = dccs.current.get(target.id);
      if (dcc !== undefined) {
        dcc.yourChannel = yourChannel;
      }
    }
    const dcc = new DataChannelConnection(pc, myChannel, target);
    dccs.current.set(target.id, dcc);

    const offer = await pc.createOffer();
    await pc.setLocalDescription(new RTCSessionDescription(offer));

    await requestOffer(chatRoomId, {
      description: offer,
      senderId: myInfo.id,
      receiverId: target.id,
    });
  }

  const subOffer = async (msg: IMessage) => {
    const {description: offer, senderId, receiverId} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo.id !== receiverId) {
      return;
    }

    const con = dccs.current.get(senderId)?.connection;
    if (con === undefined) {
      return;
    }

    await con.setRemoteDescription(new RTCSessionDescription(offer));
    const answer = await con.createAnswer();
    await con.setLocalDescription(new RTCSessionDescription(answer));

    await requestAnswer(chatRoomId, {
      description: answer,
      senderId: myInfo.id,
      receiverId: senderId,
    });

    console.log(`offer: ${msg.body}`);
  }

  const subAnswer = async (msg: IMessage) => {
    const {description: answer, senderId, receiverId} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo?.id !== receiverId) {
      return;
    }

    const con = dccs.current.get(senderId)?.connection;
    if (con === undefined) {
      return;
    }
    await con.setRemoteDescription(new RTCSessionDescription(answer));
    console.log(`answer: ${msg.body}`);
  }

  const subCandidate = async (msg: IMessage) => {
    const {candidate, senderId, receiverId} = JSON.parse(msg.body) as CandidateMessage;
    if (myInfo.id !== receiverId) {
      return;
    }

    const con = dccs.current.get(senderId)?.connection;
    if (con === undefined) {
      return;
    }
    await con.addIceCandidate(new RTCIceCandidate(candidate));
    console.log(`candidate: ${msg.body}`);
  }

  const connect = async () => {
    // check loading
    const interval = setInterval(() => {
      if (!isLoading()) {
        setLoading(false);
        clearInterval(interval);
      }
    }, 1);

    // create stomp client
    const stomp = createStompClient();
    stomp.onConnect  = async () => {
      const offerSub = stomp.subscribe(`/sub/signal/offer/${chatRoomId}`, subOffer);
      const answerSub = stomp.subscribe(`/sub/signal/answer/${chatRoomId}`, subAnswer);
      const candidateSub = stomp.subscribe(`/sub/signal/candidate/${chatRoomId}`, subCandidate);
      setStompSubs(prev => [...prev, offerSub, answerSub, candidateSub]);

      // set rtc connections
      const targets = chatUsers.map(it => it.account).filter(it => it.id !== myInfo.id)
      for (const target of targets) {
        await createPeerConnection(target);
      }
    }

    setStompClient(stomp);
    stomp.activate();
  }

  const disconnect = async () => {
    for (const dcc of dccs.current.values()) {
      console.log(`diconnect ${dcc.target.id}`);
      dcc.close();
    }

    stompSubs.forEach(it => {
      it.unsubscribe();
    });
    await stompClient?.deactivate();

    dccs.current = new Map();
    setStompSubs([]);
    setStompClient(undefined);
  }

  const send = async (message: string) => {
    const res = await sendMessage(chatRoomId, message);
    const chatMessage = await res.json();

    setChatMessages(prev => [...prev, chatMessage]);
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");

    for (const dcc of dccs.current.values()) {
      dcc.getOpenChannel()?.send(JSON.stringify(chatMessage));
    }
  }

  return {connect, disconnect, send, loading};
}

class DataChannelConnection {
  constructor(
    public readonly connection: RTCPeerConnection,
    public readonly myChannel: RTCDataChannel,
    public readonly target: Account,
    public yourChannel: RTCDataChannel | null = null,
  ) {
  }

  getOpenChannel() {
    if (this.myChannel.readyState === "open") {
      return this.myChannel;
    } else if (this.yourChannel?.readyState === "open") {
      return this.yourChannel;
    } else {
      return null;
    }
  }

  close() {
    this.connection.close();
    this.myChannel.close();
    this.yourChannel?.close();
  }
}
