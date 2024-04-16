import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";
import {Friend} from "@/graphql/types.ts";
import {css} from "@emotion/react";
import {useCreateChatRoomByFriend, useMyFriends} from "@/client/friend.ts";
import {useNavigate} from "react-router";
import {useSidebarStateStore} from "@/hooks/common/useSidebarStateStore.ts";
import React, {useState} from "react";
import {useCurChatRoomStore} from "@/hooks/chatroom/useCurChatRoomStore.ts";
import {FriendAddButton} from "@/components/account/FriendAddButton.tsx";
import {useMyInfo} from "@/hooks/common/useMyInfo.ts";
import { MyInfo } from "./MyInfo";

export function FriendSidebar() {

  const {myInfo} = useMyInfo();
  const [clickedIdx, setClickedIdx] = useState<number | undefined>(undefined);
  const {data} = useMyFriends();
  

  return (
    <div>
      <div css={frameStyle}>
        <label css={labelStyle}>친구 목록</label>
        {myInfo && (
        <FriendAddButton myInfo={myInfo} />
      )}
      </div>
      <span css={infoStyle}><MyInfo /></span>

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

const frameStyle = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top : 28px;
  padding-bottom: 20px;
  padding-left: 20px;
  padding-right: 16px;
`;

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

const labelStyle = css`
  color: white;
  font-size: 20px;
  flex-grow: 1;
`;

const clickedStyle = css`
  background-color: #76ABAE;
`;

const infoStyle = css`
  position:fixed;
  bottom:0;
  width: 20.9%;
`;


interface FriendItemProps {
  friend: Friend;
  idx: number;
  clickedIdx: number | undefined;
  setClickedIdx: React.Dispatch<React.SetStateAction<number | undefined>>;
}

function FriendItem({ friend, idx, clickedIdx, setClickedIdx }: FriendItemProps) {

  const navigate = useNavigate();

  const {setCurChatRoom} = useCurChatRoomStore();
  const {setSidebarState} = useSidebarStateStore();
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
