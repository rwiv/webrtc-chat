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

interface CompProps {
  myInfo: Account;
  chatUsers: ChatUser[];
}

const pc_config = {
  iceServers: [
    { urls: "stun:stun.l.google.com:19302" },
  ],
};

export function Comp({ myInfo, chatUsers }: CompProps) {

  const dccs = useRef<Map<number, DataChannelConnection>>(new Map());
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);
  const [loading, setLoading] = useState(true);

  const [messages, setMessages] = useState<string[]>([]);
  const [msgInput, setMsgInput] = useState("");

  function isLoading() {
    for (const dcc of dccs.current.values()) {
      if (dcc.getOpenChannel() !== null) {
        return false;
      }
    }
    return true;
  }

  async function createPeerConnection(target: Account) {
    const pc = new RTCPeerConnection(pc_config);
    pc.onicecandidate = async ev => {
      if (ev.candidate === undefined || ev.candidate === null) return;

      await requestCandidate(chatRoomId, { candidate: ev.candidate, senderId: myInfo.id, receiverId: target.id });
    }

    const dc = pc.createDataChannel(target.id.toString());
    dc.onmessage = msg => {
      setMessages(prev => [...prev, msg.data]);
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
        dcc.yourChannel = ch;
      }
    }
    const dcc = new DataChannelConnection(pc, dc, target);
    dccs.current.set(target.id, dcc);

    const offer = await pc.createOffer();
    await pc.setLocalDescription(new RTCSessionDescription(offer));

    await requestOffer(chatRoomId, { description: offer, senderId: myInfo.id, receiverId: target.id });
  }

  const subOffer = (myInfo: Account) => async (msg: IMessage) => {
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

    await requestAnswer(chatRoomId, {description: answer, senderId: myInfo.id, receiverId: senderId});

    console.log(`offer: ${msg.body}`);
  }

  const subAnswer = (myInfo: Account) => async (msg: IMessage) => {
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

  const subCandidate = (myInfo: Account) => async (msg: IMessage) => {
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

  async function initRTC() {
    const targets = chatUsers.map(it => it.account).filter(it => it.id !== myInfo.id)
    for (const target of targets) {
      await createPeerConnection(target);
    }
  }

  useEffect(() => {
    // setInterval(() => {
    //   for (const dcc of dccs.current.values()) {
    //     console.log(dcc.myChannel.readyState);
    //     console.log(dcc.yourChannel?.readyState);
    //   }
    // }, 1000);

    // check loading
    const interval = setInterval(() => {
      if (!isLoading()) {
        setLoading(false);
        clearInterval(interval);
      }
    }, 1);

    // create stomp client
    const stomp = createStompClient();
    stomp.onConnect  = () => {
      const offerSub = stomp.subscribe(`/sub/signal/offer/${chatRoomId}`, subOffer(myInfo));
      const answerSub = stomp.subscribe(`/sub/signal/answer/${chatRoomId}`, subAnswer(myInfo));
      const candidateSub = stomp.subscribe(`/sub/signal/candidate/${chatRoomId}`, subCandidate(myInfo));
      setStompSubs(prev => [...prev, offerSub, answerSub, candidateSub]);
    }
    stomp.activate();

    // set rtc connections
    initRTC()

    return () => {
      stompSubs.forEach(it => {
        it.unsubscribe();
      });
      stomp.deactivate();
    }
  }, []);

  const onStart = async () => {
    for (const dcc of dccs.current.values()) {
      const offer = await dcc.connection.createOffer();
      await dcc.connection.setLocalDescription(new RTCSessionDescription(offer));

      await requestOffer(chatRoomId, { description: offer, senderId: myInfo.id, receiverId: dcc.target.id });
    }
  }

  const onSend = () => {
    for (const dcc of dccs.current.values()) {
      dcc.getOpenChannel()?.send(msgInput);
    }
    setMsgInput("");
  }

  const onStop = () => {
    for (const dcc of dccs.current.values()) {
      console.log(`diconnect ${dcc.target.id}`);
      dcc.close();
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
        <Button disabled={loading} onClick={onSend}>Send</Button>
        {/*<Button onClick={onSend}>Send</Button>*/}
        <Button onClick={onStop}>Stop</Button>
      </div>
      {loading && (
        <div>loading...</div>
      )}
    </div>
  )
}
