import {useMyChatUsers} from "@/client/chatUser.ts";
import {css} from "@emotion/react";
import {ChatRoom} from "@/graphql/types.ts";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";
import {useNavigate} from "react-router";

export function ParticipatedChatRoomSidebar() {

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
      {getSortedChatRooms().map(chatRoom => (
        <ParticipatedChatRoomItem key={chatRoom.id} chatRoom={chatRoom} />
      ))}
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

function ParticipatedChatRoomItem({ chatRoom }: { chatRoom: ChatRoom }) {

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
    <div css={[itemStyle, getCurChatRoomStyle()]} onClick={onClick}>
      <div>
        # {chatRoom.title}
      </div>
    </div>
  )
}
