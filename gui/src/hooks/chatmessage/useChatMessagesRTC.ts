import {Account, ChatMessage, ChatUser} from "@/graphql/types.ts";
import React from "react";
import {IMessage} from "@stomp/stompjs";
import { CandidateMessage, DescriptionMessage, requestAnswer, requestCandidate, requestOffer } from "@/client/signaling.ts";
import {createStompClient} from "@/lib/web/stomp.ts";
import {sendMessage} from "@/client/chatMessage.ts";
import {ScrollType} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {DataChannelConnection, useDccMapStore} from "@/hooks/chatmessage/useDccMapStore.ts";
import {useChatMessageStompStore} from "@/hooks/chatmessage/useChatMessageStompStore.ts";
import {useApolloClient} from "@apollo/client";
import {chatRoomAndUsersByIdQL} from "@/client/chatUser.ts";

export function useChatMessagesRTC(
  chatRoomId: number,
  myInfo: Account,
  chatUsers: ChatUser[],
  setChatMessages: React.Dispatch<React.SetStateAction<ChatMessage[]>>,
  setOffset: React.Dispatch<React.SetStateAction<number>>,
  setScrollType: React.Dispatch<React.SetStateAction<ScrollType>>,
) {

  const apollo = useApolloClient();
  const {dccMap, addDcc, restore, refresh, prevCandidateMap, addPrevCandidate} = useDccMapStore();
  const {setNewStompClient} = useChatMessageStompStore();

  const onMessage = (ev: MessageEvent) => {
    const chatMessage = JSON.parse(ev.data) as ChatMessage;
    setChatMessages(prev => [...prev, chatMessage]);
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");
  }

  const createDcc = (targetId: number, readOnly: boolean) => {
    const pc = new RTCPeerConnection({
      iceServers: [ { urls: "stun:stun.l.google.com:19302" } ],
    });
    pc.onicecandidate = async ev => {
      if (ev.candidate === undefined || ev.candidate === null) return;

      await requestCandidate(chatRoomId, {
        candidate: ev.candidate,
        senderId: myInfo.id,
        receiverId: targetId,
      });
    }

    let myChannel: RTCDataChannel | undefined;
    if (!readOnly) {
      myChannel = pc.createDataChannel(targetId.toString());
      myChannel.onmessage = onMessage;
      myChannel.onopen = refresh;
      myChannel.onclose = refresh;
    }

    pc.ondatachannel = async ev => {
      const yourChannel = ev.channel;
      yourChannel.onmessage = onMessage;
      yourChannel.onopen = refresh;
      yourChannel.onclose =refresh;

      const dcc = dccMap.get(targetId);
      if (dcc !== undefined) {
        dcc.setYourChannel(yourChannel);
      }
    }
    const dcc = new DataChannelConnection(pc, targetId, myChannel);
    addDcc(dcc);
    return dcc;
  }

  const reqOffer = async (pc: RTCPeerConnection, target: Account) => {
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

    let dcc = dccMap.get(senderId);
    if (dcc === undefined) {
      dcc = createDcc(senderId, true);
      await apollo.refetchQueries({ include: [ chatRoomAndUsersByIdQL(chatRoomId) ] });
    }
    const con = dcc!.connection;

    await con.setRemoteDescription(new RTCSessionDescription(offer));
    const answer = await con.createAnswer();
    await con.setLocalDescription(new RTCSessionDescription(answer));
    await dcc.emitRemoteDesc(prevCandidateMap);

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

    const dcc = dccMap.get(senderId);
    const con = dcc?.connection;
    if (con === undefined) {
      console.log("not found dcc in answer");
      return;
    }
    await con.setRemoteDescription(new RTCSessionDescription(answer));
    await dcc?.emitRemoteDesc(prevCandidateMap);
    console.log(`answer: ${msg.body}`);
  }

  const subCandidate = async (msg: IMessage) => {
    const {candidate, senderId, receiverId} = JSON.parse(msg.body) as CandidateMessage;
    if (myInfo.id !== receiverId) {
      return;
    }

    const dcc = dccMap.get(senderId);
    const con = dcc?.connection;
    if (con === undefined) {
      console.log("not found dcc in candidate");
      return;
    }

    if (con.remoteDescription === null) {
      addPrevCandidate(senderId, new RTCIceCandidate(candidate));
    } else {
      await con.addIceCandidate(new RTCIceCandidate(candidate));
    }
    console.log(`candidate: ${msg.body}`);
  }

  const connect = () => {
    console.log("start connect")
    const stomp = createStompClient();
    stomp.onConnect  = async () => {
      const offerSub = stomp.subscribe(`/sub/signal/offer/${chatRoomId}`, subOffer);
      const answerSub = stomp.subscribe(`/sub/signal/answer/${chatRoomId}`, subAnswer);
      const candidateSub = stomp.subscribe(`/sub/signal/candidate/${chatRoomId}`, subCandidate);
      setNewStompClient(stomp, [offerSub, answerSub, candidateSub]);

      // set rtc connections
      const targets = chatUsers.map(it => it.account).filter(it => it.id !== myInfo.id)
      for (const target of targets) {
        const dcc = createDcc(target.id, false);
        await reqOffer(dcc.connection, target);
      }
    }
    stomp.activate();
  }

  const disconnect = () => {
    console.log("start disconnect")
    restore();
    setNewStompClient(undefined, []);
  }

  const send = async (message: string) => {
    const res = await sendMessage(chatRoomId, message);
    const chatMessage = await res.json();

    setChatMessages(prev => [...prev, chatMessage]);
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");

    for (const dcc of dccMap.values()) {
      dcc.getOpenChannel()?.send(JSON.stringify(chatMessage));
    }
  }

  return {connect, disconnect, send};
}
