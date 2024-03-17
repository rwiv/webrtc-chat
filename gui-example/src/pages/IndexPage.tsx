import {useChatRoomsAll, useCreateChatRoom, useDeleteChatRoom} from "@/client/chatRoom.tsx";
import {logout, useMe} from "@/client/account.tsx";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import {ChatRoomCreateRequest, ChatRoomType} from "@/graphql/types.ts";
import {Link} from "react-router-dom";

export default function IndexPage() {
  const [chatRoomInput, setChatRoomInput] = useState("");

  const {data: me, error} = useMe();
  const {data: chatRooms} = useChatRoomsAll();
  const {createChatRoom} = useCreateChatRoom();
  const {deleteChatRoom} = useDeleteChatRoom();

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
          <Link to={`/chat-rooms/${chatRoom.id}`}>
            <span>{chatRoom.title}</span>
          </Link>
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
