import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";
import {Friend} from "@/graphql/types.ts";
import {css} from "@emotion/react";
import {useCreateChatRoomByFriend, useMyFriends} from "@/client/friend.ts";
import {useNavigate} from "react-router";
import {useSidebarState} from "@/hooks/global/useSidebarState.ts";
import React, {useState} from "react";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";

export function FriendSidebar() {

  const [clickedIdx, setClickedIdx] = useState<number | undefined>(undefined);
  const {data} = useMyFriends();

  return (
    <div>
      {data?.account?.friends?.map((friend, idx) => (
        <FriendItem
          key={friend.to?.id}
          friend={friend}
          idx={idx}
          clickedIdx={clickedIdx}
          setClickedIdx={setClickedIdx}
        />
      ))}
    </div>
  )
}

const itemStyle = css`
    display: flex;
    align-items: center;
    padding: 1rem;
    color: #ffffff;
    
    -webkit-user-select:none;
    -moz-user-select:none;
    -ms-user-select:none;
    user-select:none;
`;

const clickedStyle = css`
    background-color: #76ABAE;
`;

interface FriendItemProps {
  friend: Friend;
  idx: number;
  clickedIdx: number | undefined;
  setClickedIdx: React.Dispatch<React.SetStateAction<number | undefined>>;
}

function FriendItem({ friend, idx, clickedIdx, setClickedIdx }: FriendItemProps) {

  const navigate = useNavigate();

  const {setCurChatRoom} = useCurChatRoom();
  const {setSidebarState} = useSidebarState();
  const {createChatRoomByFriend} = useCreateChatRoomByFriend();

  const onClick = () => {
    setClickedIdx(idx);
  }

  const onDoubleClick = async () => {
    const variables = { friendId: friend.id };
    const res = await createChatRoomByFriend({ variables });
    const chatRoom = res.data?.createChatRoomByFriend;
    if (chatRoom !== undefined) {
      setCurChatRoom(chatRoom);
      setSidebarState("CHATROOM");
      navigate(`/chat-rooms/${chatRoom.id}`);
    }
  }

  const getClickedStyle = () => {
    if (clickedIdx === idx) {
      return clickedStyle;
    }
    return {};
  }

  return (
    <div
      css={[itemStyle, getClickedStyle()]}
      onClick={onClick}
      onDoubleClick={onDoubleClick}
    >
      <img
        src={`${consts.endpoint}${friend.to?.avatarUrl}`}
        css={iconStyle}
        alt="chat-user-avatar"
      />
      <div css={{marginLeft: "0.7rem"}}>
        <div className="font-semibold">{friend.to?.nickname}</div>
        <div>{friend.to?.username}</div>
      </div>
    </div>
  )
}
