import {Button} from "@/components/ui/button.tsx";
import {createStompClient} from "@/lib/web/stomp.ts";
import {IMessage, StompSubscription} from "@stomp/stompjs";
import {useEffect, useState} from "react";
import {Account} from "@/graphql/types.ts";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {Input} from "@/components/ui/input.tsx";
import { requestCandidate, requestAnswer, requestOffer, DescriptionMessage, CandidateMessage } from "@/client/signaling.ts";

const pc_config = {
  iceServers: [
    {
      urls: "stun:stun.l.google.com:19302",
    },
  ],
};

const chatRoomId = 1;
const myId = 2;
const yourId = 3;

interface DataChannelConnection {
  connection: RTCPeerConnection;
  channel: RTCDataChannel | null;
}

export function TestPage() {

  const {myInfo} = useMyInfo();

  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);
  const [dccs, setDccs] = useState<{ [accountId: string]: DataChannelConnection }>({});

  const [messages, setMessages] = useState<string[]>([]);
  const [msgInput, setMsgInput] = useState("");

  function createPeerConnection(senderId: number, receiverId: number) {
    const pc = new RTCPeerConnection(pc_config);
    pc.onicecandidate = async ev => {
      if (ev.candidate === undefined || ev.candidate === null) return;
      // if (ev.candidate.type !== candidateType) return;

      await requestCandidate(chatRoomId, { candidate: ev.candidate, senderId, receiverId })
    }
    return pc;
  }

  const subOffer = (myInfo: Account) => async (msg: IMessage) => {
    const {description: offer} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo.id === myId) {
      return;
    }

    const con = dccs[myId]?.connection;
    if (con === undefined) {
      return;
    }

    await con.setRemoteDescription(new RTCSessionDescription(offer));
    const answer = await con.createAnswer();
    await con.setLocalDescription(new RTCSessionDescription(answer));

    await requestAnswer(chatRoomId, {description: answer, senderId: yourId});

    console.log(`offer: ${msg.body}`);
  }

  const subAnswer = (myInfo: Account) => async (msg: IMessage) => {
    const {description: answer, senderId} = JSON.parse(msg.body) as DescriptionMessage;
    if (myInfo?.id === yourId) {
      return;
    }

    const con = dccs[senderId]?.connection;
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
    if (myInfo.id !== receiverId) {
      return;
    }

    const con = dccs[senderId]?.connection;
    if (con === undefined) {
      return;
    }
    await con.addIceCandidate(new RTCIceCandidate(candidate));
    console.log(`candidate: ${msg.body}`);
  }

  useEffect(() => {
    if (myInfo === undefined || myInfo === null) return;

    if (myInfo.id === myId) {
      const pc = createPeerConnection(myInfo.id, yourId)

      const dc = pc.createDataChannel("write");
      dc.onmessage = msg => {
        console.log(msg);
      }
      setDccs(prev => {
        prev[yourId] = { connection: pc, channel: dc };
        return prev;
      });
    }

    if (myInfo.id === yourId) {
      const pc = createPeerConnection(myInfo.id, myId)

      pc.ondatachannel = async ev => {
        const ch = ev.channel;
        ch.onmessage = msg => {
          setMessages(prev => [...prev, msg.data]);
          console.log(msg);
        }

        setDccs(prev => {
          const {connection} = prev[myId]
          prev[myId] = { connection, channel: ch };
          return prev;
        });
      }

      setDccs(prev => {
        prev[myId] = { connection: pc, channel: null };
        return prev;
      });
    }

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
  }, [myInfo]);

  const onStart = async () => {
    if (myInfo === undefined || myInfo === null) return;

    const con = dccs[yourId]?.connection;
    if (con === undefined) {
      return;
    }
    const offer = await con.createOffer();
    await con.setLocalDescription(new RTCSessionDescription(offer));

    await requestOffer(chatRoomId, { description: offer, senderId: myInfo.id });
  }

  const onSend = () => {
    const dcc = dccs[yourId];
    if (dcc === undefined) {
      throw Error("not found dcc");
    }
    dcc.channel?.send(msgInput);
    setMsgInput("");
  }

  const onStop = () => {
    for (const [, dcc] of Object.entries(dccs)) {
      dcc.connection.close();
      dcc.channel?.close();
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
