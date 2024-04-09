import React, {useState} from "react";
import {Client, IMessage, StompSubscription} from "@stomp/stompjs";
import {ChatMessage, Query} from "@/graphql/types.ts";
import {chatMessageQL} from "@/client/chatMessage.ts";
import {createStompClient} from "@/lib/web/stomp.ts";
import {useApolloClient} from "@apollo/client";

export function useChatMessagesServerBasedStomp(
  chatRoomId: number,
  setChatMessages: React.Dispatch<React.SetStateAction<ChatMessage[]>>,
  setOffset: React.Dispatch<React.SetStateAction<number>>,
  setScrollType: React.Dispatch<React.SetStateAction<"BOTTOM" | "TOP">>,
) {

  const apolloClient = useApolloClient();

  const [stompClient, setStompClient] = useState<Client>();
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);

  async function subscribe(msg: IMessage) {
    const body = JSON.parse(msg.body) as { id: number, num: number };
    const res = await apolloClient.query<Query>({
      query: chatMessageQL, variables: { id: body.id }, fetchPolicy: "network-only",
    });
    const chatMessage = res.data.chatMessage;
    if (chatMessage === undefined || chatMessage === null) return;

    setChatMessages(prev => {
      if (prev.find(it => it.id === body.id) !== undefined) {
        return prev;
      }
      return [...prev, chatMessage];
    });
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");
  }

  function connect() {
    console.log("connect");
    if (stompClient !== undefined) {
      return;
    }
    const newStompClient = createStompClient();
    newStompClient.onConnect  = () => {
      const dest = `/sub/message/${chatRoomId}`;
      const sub = newStompClient.subscribe(dest, subscribe);
      setStompSubs(prev => {
        prev.push(sub);
        return prev;
      });
    }
    newStompClient.activate();
    setStompClient(newStompClient);
  }

  function disconnect() {
    console.log("disconnect");

    stompSubs.forEach(it => {
      it.unsubscribe();
    });

    stompClient?.deactivate();
    setStompClient(undefined);
  }

  return {connect, disconnect};
}
