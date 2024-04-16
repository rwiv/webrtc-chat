import {useMyChatUsers} from "@/client/chatUser.ts";
import {css} from "@emotion/react";
import {Account, ChatRoom} from "@/graphql/types.ts";
import {useCurChatRoomStore} from "@/hooks/chatroom/useCurChatRoomStore.ts";
import {useNavigate} from "react-router";
import {ParticipatedChatRoomContextMenu} from "@/components/chatroom/ParticipatedChatRoomContextMenu.tsx";
import {useMyInfo} from "@/hooks/common/useMyInfo.ts";
import { MyInfo } from "../account/MyInfo";

export function ParticipatedChatRoomSidebar() {

  const {myInfo} = useMyInfo();
  const {data} = useMyChatUsers();
  const chatUsers = data?.account?.chatUsers;

  const getSortedChatRooms = () => {
    if (chatUsers === undefined || chatUsers === null) {
      return [];
    }
    return [...chatUsers]
      .sort((a, b) => Date.parse(b.createdAt) - Date.parse(a.createdAt))
      .map(it => it.chatRoom)
  }

  return (
    <div>
      <div css={frameStyle}>
        <label css={labelStyle}>참여중인 채팅방</label>
      </div>
      <span css={infoStyle}><MyInfo /></span>
      {myInfo && (
        getSortedChatRooms().map(chatRoom => (
          <ParticipatedChatRoomItem key={chatRoom.id} chatRoom={chatRoom} myInfo={myInfo} />
        ))
      )}
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

const labelStyle = css`
  color: white;
  font-size: 20px;
`;

const itemStyle = css`
  color: #ffffff;
  padding: 0.7rem;
  margin: 0.3rem;
  cursor: pointer;
`;

const curChatRoomStyle = css`
  background-color: #76ABAE;
`;

const infoStyle = css`
  position:fixed;
  bottom:0;
  width: 20.9%;
`;

interface ParticipatedChatRoomItemProps {
  chatRoom: ChatRoom;
  myInfo: Account;
}

function ParticipatedChatRoomItem({ chatRoom, myInfo }: ParticipatedChatRoomItemProps) {

  const navigate = useNavigate();
  const {curChatRoom, setCurChatRoom} = useCurChatRoomStore();

  const onClick = () => {
    setCurChatRoom(chatRoom);
    navigate(`/chat-rooms/${chatRoom.id}`);
  }

  const getCurChatRoomStyle = () => {
    if (curChatRoom?.id === chatRoom.id) {
      return curChatRoomStyle;
    }
    return {};
  }

  return (
    <ParticipatedChatRoomContextMenu chatRoom={chatRoom} myInfo={myInfo}>
      <div css={[itemStyle, getCurChatRoomStyle()]} onClick={onClick}>
        <div>
          # {chatRoom.title}
        </div>
      </div>
    </ParticipatedChatRoomContextMenu>
  )
}
