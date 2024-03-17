import {useChatRoomsAll, useCreateChatRoom, useDeleteChatRoom} from "@/client/chatRoom.ts";
import {logout, useMe} from "@/client/account.ts";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import {ChatRoom, ChatRoomCreateRequest, ChatRoomType, Query} from "@/graphql/types.ts";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useApolloClient} from "@apollo/client";

export default function IndexPage() {
  const [chatRoomInput, setChatRoomInput] = useState("");

  const {data: me, error} = useMe();
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

  const onClickLink = async (
    e: React.MouseEvent<HTMLAnchorElement, MouseEvent>,
    chatRoom: ChatRoom,
  ) => {
    e.preventDefault();

    const data = await client.query<Query>({
      query: chatRoomAndUsersQL,
      variables: { id: chatRoom.id },
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
          <button onClick={onLogout}>logout</button>
          <br/>
          <br/>
        </div>
      )}
      {chatRooms?.chatRoomsAll?.map(chatRoom => (
        <div key={chatRoom.id}>
          <a href={""} onClick={e => onClickLink(e, chatRoom)}>
            <span>{chatRoom.title}</span>
          </a>
          <span>  </span>
          <button onClick={() => onDeleteChatRoom(chatRoom.id)}>x</button>
        </div>
      ))}
      <div>
        <input
          onChange={e => handleChange(e, setChatRoomInput)}
          value={chatRoomInput}
        />
        <button onClick={() => onAddChatRoom()}>add</button>
      </div>
    </>
  )
}
