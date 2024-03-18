import {useNavigate, useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {chatRoomAndMessagesQL, sendMessage, useChatRoomAndMessages} from "@/client/chatMessage.ts";
import {useApolloClient} from "@apollo/client";
import {Client, StompSubscription} from "@stomp/stompjs";
import {consts} from "@/configures/consts.ts";
import {useDeleteChatUserMe} from "@/client/chatUser.ts";
import {getQueryName} from "@/client/graphql_utils.ts";

export function ChatRoomPage() {

  const params = useParams();
  const chatRoomId = params["chatRoomId"] as string;
  if (chatRoomId === undefined) {
    throw Error("chatRoomId is null");
  }

  const [chatMessageInput, setChatMessageInput] = useState("");
  const [stompClient, setStompClient] = useState<Client>();
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);

  const {data} = useChatRoomAndMessages(parseInt(chatRoomId));
  const {deleteChatUserMe} = useDeleteChatUserMe();

  const apolloClient = useApolloClient();
  const navigate = useNavigate();

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
            include: [getQueryName(chatRoomAndMessagesQL)],
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

  const onExit = async () => {
    const variables = { chatRoomId: parseInt(chatRoomId) };
    const res = await deleteChatUserMe({ variables })
    console.log(res);
    navigate("/");
  }

  return (
    <>
      <div>{chatRoomId}</div>
      {data?.chatRoom?.chatUsers?.map(chatUser => (
        <div key={chatUser.id}>{`${chatUser.account.nickname}`}</div>
      ))}
      <br/>
      {data?.chatRoom?.chatMessages?.map(chatMessage => (
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
      <button onClick={onExit}>exit</button>
    </>
  )
}
