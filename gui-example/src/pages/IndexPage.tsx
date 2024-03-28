import {useCreateChatRoom} from "@/client/chatRoom.ts";
import {logout, useMyInfo} from "@/client/account.ts";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import {ChatRoomCreateRequest, ChatRoomType} from "@/graphql/types.ts";
import {Button} from "@/components/ui/button.tsx";
import {Input} from "@/components/ui/input.tsx";
import {HStack} from "@/lib/layouts.tsx";
import {ChatRoomList} from "@/components/chatroom/ChatRoomList.tsx";
import {ChatRoomListReverse} from "@/components/chatroom/ChatRoomListReverse.tsx";

export default function IndexPage() {
  const [chatRoomInput, setChatRoomInput] = useState("");

  const {data: me, error} = useMyInfo();

  const {createChatRoom} = useCreateChatRoom();

  const navigate = useNavigate();

  useEffect(() => {
    if (error) {
      navigate("/account-select");
    }
  }, [error])

  const onAddChatRoom = async () => {
    const variables: {req: ChatRoomCreateRequest} = {
      req: {
        password: null,
        title: chatRoomInput,
        type: ChatRoomType.Public,
      },
    };
    const res = await createChatRoom({variables});
    console.log(res.data?.createChatRoom);
    setChatRoomInput("");
  }

  const onLogout = async () => {
    await logout();
    navigate("/account-select");
  }
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    setState: React.Dispatch<React.SetStateAction<string>>,
  ) => {
    setState(e.target.value);
  };

  return (
    <>
      {me && (
        <div>
          <div>{me?.account?.username}</div>
          <div>{me.account?.nickname}</div>
          <Button onClick={onLogout}>logout</Button>
          <br/>
          <br/>
        </div>
      )}
      <ChatRoomList />
      <ChatRoomListReverse />
      <HStack>
        <Input
          className="w-52"
          onChange={(e: any) => handleChange(e, setChatRoomInput)}
          value={chatRoomInput}
        />
        <Button onClick={() => onAddChatRoom()}>add</Button>
      </HStack>
    </>
  )
}
