import {useChatRoomsAll, useCreateChatRoom, useDeleteChatRoom} from "@/client/chatRoom.ts";
import {logout, useMyInfo} from "@/client/account.ts";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import {ChatRoom, ChatRoomCreateRequest, ChatRoomType, Query} from "@/graphql/types.ts";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useApolloClient} from "@apollo/client";
import {Button} from "@/components/ui/button.tsx";
import {Input} from "@/components/ui/input.tsx";
import {HStack} from "@/lib/layouts.tsx";

export default function IndexPage() {
  const [chatRoomInput, setChatRoomInput] = useState("");

  const {data: me, error} = useMyInfo();
  const {data: chatRooms} = useChatRoomsAll();
  const {createChatRoom} = useCreateChatRoom();
  const {deleteChatRoom} = useDeleteChatRoom();
  const {createChatUser} = useCreateChatUser();

  const navigate = useNavigate();
  const client = useApolloClient();

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

  const onDeleteChatRoom = async (chatRoomId: number) => {
    const variables = {
      chatRoomId,
    }
    const res = await deleteChatRoom({ variables})
    console.log(res.data?.deleteChatRoom);
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

  const onClickLink = async (chatRoom: ChatRoom) => {
    const data = await client.query<Query>({
      query: chatRoomAndUsersQL,
      variables: { id: chatRoom.id },
      // network-only로 설정하지 않으면 이전 chatRoom을 exit해도
      // 이전 cache가 적용되어 exit하지 않은 것으로 처리되어 에러 발생
      fetchPolicy: "network-only",
    });
    const filtered = data.data?.chatRoom?.chatUsers?.filter(it => {
      return it.account.id === me?.account?.id;
    });
    if (filtered?.length === 0) {
      const variables = {
        chatRoomId: chatRoom.id,
        password: null,
      }
      const res = await createChatUser({variables})
      console.log(res.data);
    }
    navigate(`/chat-rooms/${chatRoom.id}`);
  }

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
      {chatRooms?.chatRoomsAll?.map(chatRoom => (
        <div key={chatRoom.id}>
          <Button
            variant="link"
            onClick={() => onClickLink(chatRoom)}
          >
            {chatRoom.title}
          </Button>
          <Button variant="ghost" onClick={() => onDeleteChatRoom(chatRoom.id)}>x</Button>
        </div>
      ))}
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
