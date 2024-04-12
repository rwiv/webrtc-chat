import {useMyChatUsers} from "@/client/chatUser.ts";
import {css} from "@emotion/react";
import {Account, ChatRoom} from "@/graphql/types.ts";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";
import {useNavigate} from "react-router";
import {ParticipatedChatRoomContextMenu} from "@/components/chatroom/ParticipatedChatRoomContextMenu.tsx";
import {useMyInfo} from "@/hooks/useMyInfo.ts";

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
      {myInfo && (
        getSortedChatRooms().map(chatRoom => (
          <ParticipatedChatRoomItem key={chatRoom.id} chatRoom={chatRoom} myInfo={myInfo} />
        ))
      )}
    </div>
  )
}

const itemStyle = css`
  color: #ffffff;
  padding: 0.7rem;
  margin: 0.3rem;
  cursor: pointer;
`;

const curChatRoomStyle = css`
  background-color: #76ABAE;
`;

interface ParticipatedChatRoomItemProps {
  chatRoom: ChatRoom;
  myInfo: Account;
}

function ParticipatedChatRoomItem({ chatRoom, myInfo }: ParticipatedChatRoomItemProps) {

  const navigate = useNavigate();
  const {curChatRoom, setCurChatRoom} = useCurChatRoom();

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
