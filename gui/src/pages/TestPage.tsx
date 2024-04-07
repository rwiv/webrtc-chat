import {Button} from "@/components/ui/button.tsx";
import {createStompClient} from "@/lib/web/stomp.ts";
import {IMessage, StompSubscription} from "@stomp/stompjs";
import {useEffect, useState} from "react";
import {consts} from "@/configures/consts.ts";
import {login, LoginRequest, myInfoQL} from "@/client/account.ts";
import {useQuery} from "@apollo/client";
import {Query} from "@/graphql/types.ts";
import {post} from "@/lib/web/http.ts";

const pc_config = {
  iceServers: [
    {
      urls: "stun:stun.l.google.com:19302",
    },
  ],
};

const chatRoomId = 1;
const yourId = 3;

interface DescriptionMsg {
  type: string;
  sdp: string;
  sentBy: number;
}

export function TestPage() {

  const {data: myInfoData, error} = useQuery<Query>(myInfoQL);
  const myInfo = myInfoData?.account;

  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);
  let pcs = useState<{ [accountId: string]: RTCPeerConnection }>();

  useEffect(() => {
    if (error !== undefined) {
      const req: LoginRequest = {
        username: "user1@gmail.com",
        password: "1234",
      };
      login(req, false);
    }
  }, [error]);

  useEffect(() => {

    const stomp = createStompClient();
    stomp.onConnect  = () => {
      const offerSub = stomp.subscribe(`/sub/signal/offer/${chatRoomId}`, (msg: IMessage) => {
        const desc = JSON.parse(msg.body) as DescriptionMsg;
        console.log(desc)
        console.log(`offer: ${desc}`);
      });
      const answerSub = stomp.subscribe(`/sub/signal/answer/${chatRoomId}`, (msg: IMessage) => {
        console.log(`answer: ${msg.body}`);
      });
      const candidateSub = stomp.subscribe(`/sub/signal/candidate/${chatRoomId}`, (msg: IMessage) => {
        console.log(`candidate: ${msg.body}`);
      });
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
    const pc = new RTCPeerConnection(pc_config);
    const {type, sdp} = await pc.createOffer();
    await post(
      `${consts.endpoint}/api/signal/offer/${chatRoomId}`,
      { type, sdp, sentBy: myInfo?.id },
    );
  }

  const onSend = () => {

  }

  const onStop = () => {

  }

  return (
    <div>
      <Button onClick={onStart}>Start</Button>
      <Button onClick={onSend}>Send</Button>
      <Button onClick={onStop}>Stop</Button>
    </div>
  )
}
