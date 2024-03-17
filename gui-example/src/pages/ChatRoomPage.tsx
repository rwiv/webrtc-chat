import {useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {sendMessage, useChatMessagesChatRoomId} from "@/client/chatMessage.tsx";
import {useApolloClient} from "@apollo/client";
import {Client, StompSubscription} from "@stomp/stompjs";
import {consts} from "@/configures/consts.ts";

export function ChatRoomPage() {

  const params = useParams();
  const chatRoomId = params["chatRoomId"] as string;
  if (chatRoomId === undefined) {
    throw Error("chatRoomId is null");
  }

  const apolloClient = useApolloClient();
  const [stompClient, setStompClient] = useState<Client>();
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);

  // const {data: chatMessages} = useChatMessages();
  const {data: chatMessages} = useChatMessagesChatRoomId(parseInt(chatRoomId));
  const [chatMessageInput, setChatMessageInput] = useState("");

  useEffect(() => {
    connect();
    return disconnect;
  }, []);

  function connect() {
    console.log("connect");
    if (stompClient !== undefined) {
      return;
    }
    const newStompClient = new Client({
      brokerURL: `ws://${consts.domain}/stomp`,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        const sub = newStompClient.subscribe(`/sub/message/${chatRoomId}`, msg => {
          apolloClient.refetchQueries({
            include: ["ChatMessagesByChatRoomId"],
          });
          console.log(msg);
        });
        setStompSubs(prev => {
          prev.push(sub);
          return prev;
        });
      }
    });
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

  async function send() {
    if (!chatRoomId) {
      throw Error("chatRoomId is null");
    }
    await sendMessage(chatRoomId, chatMessageInput);
    setChatMessageInput("");
  }

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    setState: React.Dispatch<React.SetStateAction<string>>,
  ) => {
    setState(e.target.value);
  };

  return (
    <>
      <div>{chatRoomId}</div>
      {chatMessages?.chatMessagesByChatRoomId?.map(chatMessage => (
        <div key={chatMessage.id}>
          {`${chatMessage.createAccount.nickname}: ${chatMessage.content}`}
        </div>
        ))}
      <div>
      <input
          onChange={e => handleChange(e, setChatMessageInput)}
          value={chatMessageInput}
        />
        <button onClick={() => send()}>send</button>
      </div>
    </>
  )
}
