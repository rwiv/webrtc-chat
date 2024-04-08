import {Button} from "@/components/ui/button.tsx";
import {createStompClient} from "@/lib/web/stomp.ts";
import {IMessage, StompSubscription} from "@stomp/stompjs";
import {useEffect, useRef, useState} from "react";
import {Account, ChatUser} from "@/graphql/types.ts";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {Input} from "@/components/ui/input.tsx";
import { requestCandidate, requestAnswer, requestOffer, DescriptionMessage, CandidateMessage } from "@/client/signaling.ts";
import {useChatRoomAndUsers} from "@/client/chatUser.ts";

const chatRoomId = 20;

export function TestPage() {

  const {myInfo} = useMyInfo();
  const {data: users} = useChatRoomAndUsers(chatRoomId);
  const chatUsers = users?.chatRoom?.chatUsers;

  return (
    <div>
      {myInfo !== undefined && myInfo !== null
        && chatUsers !== undefined && chatUsers !== null && (
        <Comp myInfo={myInfo} chatUsers={chatUsers} />
      )}
    </div>
  )
}

const pc_config = {
  iceServers: [
    {
      urls: "stun:stun.l.google.com:19302",
    },
  ],
};

const yourId = 3;

interface CompProps {
  myInfo: Account;
  chatUsers: ChatUser[];
}

class DataChannelConnection {
  constructor(
    public readonly connection: RTCPeerConnection,
    public readonly writeChannel: RTCDataChannel,
    public readonly target: Account,
    public readChannel: RTCDataChannel | null = null,
  ) {
  }
}

export function Comp({ myInfo, chatUsers }: CompProps) {

  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);
  const dccs = useRef<Map<number, DataChannelConnection>>(new Map());


  const [messages, setMessages] = useState<string[]>([]);
  const [msgInput, setMsgInput] = useState("");

  async function createPeerConnection(senderId: number, receiverId: number) {
    const pc = new RTCPeerConnection(pc_config);
    pc.onicecandidate = async ev => {
      if (ev.candidate === undefined || ev.candidate === null) return;

      await requestCandidate(chatRoomId, { candidate: ev.candidate, senderId, receiverId })
    }
    return pc;
  }

  const subOffer = (myInfo: Account) => async (msg: IMessage) => {
    const {description: offer, senderId} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo.id === senderId) {
      return;
    }

    const con = dccs.current.get(senderId)?.connection;
    if (con === undefined) {
      return;
    }

    await con.setRemoteDescription(new RTCSessionDescription(offer));
    const answer = await con.createAnswer();
    await con.setLocalDescription(new RTCSessionDescription(answer));

    await requestAnswer(chatRoomId, {description: answer, senderId: myInfo.id});

    console.log(`offer: ${msg.body}`);
  }

  const subAnswer = (myInfo: Account) => async (msg: IMessage) => {
    const {description: answer, senderId} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo?.id === senderId) {
      return;
    }

    const con = dccs.current.get(senderId)?.connection;
    if (con === undefined) {
      return;
    }
    if (con.localDescription !== undefined) {
      await con.setRemoteDescription(new RTCSessionDescription(answer));
    }
    console.log(`answer: ${msg.body}`);
  }

  const subCandidate = (myInfo: Account) => async (msg: IMessage) => {
    const {candidate, senderId, receiverId} = JSON.parse(msg.body) as CandidateMessage;
    console.log(msg.body)
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

  async function init() {
    const targets = chatUsers.map(it => it.account).filter(it => it.id !== myInfo.id)
    for (const target of targets) {
      const pc = await createPeerConnection(myInfo.id, target.id);

      const dc = pc.createDataChannel("write");
      dc.onmessage = msg => {
        console.log(msg);
      }

      pc.ondatachannel = async ev => {
        const ch = ev.channel;
        ch.onmessage = msg => {
          setMessages(prev => [...prev, msg.data]);
          console.log(msg);
        }

        const dcc = dccs.current.get(target.id);
        if (dcc !== undefined) {
          dcc.readChannel = ch;
        }
      }
      dccs.current.set(target.id, new DataChannelConnection(pc, dc, target));
    }
  }

  useEffect(() => {
    init();
    const stomp = createStompClient();
    stomp.onConnect  = () => {
      const offerSub = stomp.subscribe(`/sub/signal/offer/${chatRoomId}`, subOffer(myInfo));
      const answerSub = stomp.subscribe(`/sub/signal/answer/${chatRoomId}`, subAnswer(myInfo));
      const candidateSub = stomp.subscribe(`/sub/signal/candidate/${chatRoomId}`, subCandidate(myInfo));
      setStompSubs(prev => [...prev, offerSub, answerSub, candidateSub]);
    }
    stomp.activate();

    return () => {
      stompSubs.forEach(it => {
        it.unsubscribe();
      });
      stomp.deactivate();
    }
  }, []);

  const onStart = async () => {
    const con = dccs.current.get(yourId)?.connection;
    if (con === undefined) {
      return;
    }
    const offer = await con.createOffer();
    await con.setLocalDescription(new RTCSessionDescription(offer));

    await requestOffer(chatRoomId, { description: offer, senderId: myInfo.id });
  }

  const onSend = () => {
    const dcc = dccs.current.get(yourId);
    if (dcc === undefined) {
      throw Error("not found dcc");
    }
    dcc.writeChannel.send(msgInput);
    setMsgInput("");
  }

  const onStop = () => {
    for (const [, dcc] of dccs.current.entries()) {
      console.log(`diconnect ${dcc.target.id}`);
      dcc.connection.close();
      dcc.writeChannel.close();
      dcc.readChannel?.close();
    }
  }

  return (
    <div>
      {messages.map((msg, idx) => (
        <div key={idx}>{msg}</div>
      ))}
      <Input
        id="name"
        className="w-56 m-3"
        onChange={event => setMsgInput(event.target.value)}
        value={msgInput}
      />
      <div className="m-3">
        <Button onClick={onStart}>Start</Button>
        <Button onClick={onSend}>Send</Button>
        <Button onClick={onStop}>Stop</Button>
      </div>
    </div>
  )
}
