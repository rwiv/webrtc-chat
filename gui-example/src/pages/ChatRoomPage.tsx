import {useNavigate, useParams} from "react-router";
import React, {useState} from "react";
import {sendMessage} from "@/client/chatMessage.ts";
import {useChatRoomAndUsers, useDeleteChatUserMe} from "@/client/chatUser.ts";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {HStack} from "@/lib/layouts.tsx";
import {ChatMessageList} from "@/components/chatmessage/ChatMessageList.tsx";

export function ChatRoomPage() {

  const params = useParams();
  const navigate = useNavigate();
  const chatRoomId = getChatRoomId();

  const {data: users} = useChatRoomAndUsers(chatRoomId)
  const [chatMessageInput, setChatMessageInput] = useState("");
  const {deleteChatUserMe} = useDeleteChatUserMe();

  function getChatRoomId() {
    const chatRoomId = params["chatRoomId"];
    if (chatRoomId === undefined) {
      throw Error("chatRoomId is null");
    }
    const idNum = parseInt(chatRoomId);
    if (isNaN(idNum)) {
      throw Error("chatRoomId is NaN");
    }
    return idNum;
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
    const variables = { chatRoomId };
    const res = await deleteChatUserMe({ variables })
    console.log(res);
    navigate("/");
  }

  return (
    <>
      <div>{chatRoomId}</div>
      {users?.chatRoom?.chatUsers?.map(chatUser => (
        <div key={chatUser.id}>{`${chatUser.account.nickname}`}</div>
      ))}
      <br/>
      <ChatMessageList chatRoomId={chatRoomId} />
      <HStack className="m-2">
        <Input
          className="w-56"
          onChange={(e: any) => handleChange(e, setChatMessageInput)}
          value={chatMessageInput}
        />
        <Button onClick={() => send()}>send</Button>
      </HStack>
      <Button className="m-2" onClick={onExit}>exit</Button>
    </>
  )
}
