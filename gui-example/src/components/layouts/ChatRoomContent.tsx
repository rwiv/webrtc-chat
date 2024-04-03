import {ChatUserSidebar} from "@/components/chatuser/ChatUserSidebar.tsx";
import {ChatMessagesContent} from "@/components/chatmessage/ChatMessagesContent.tsx";
import {css} from "@emotion/react";
import {ChatRoomContentHeader} from "@/components/layouts/ChatRoomContentHeader.tsx";

const columnStyle = css`
    flex-grow: 1;
    background-color: #eeeeee;
    display: flex;
    flex-direction: column;
    border-right: 2px solid #e2e2e2;
`;

interface ChatRoomContentProps {
  chatRoomId: number | null;
}

export function ChatRoomContent({ chatRoomId }: ChatRoomContentProps) {

  return (
    <>
      <div css={columnStyle}>
        <ChatRoomContentHeader chatRoomId={chatRoomId} />
        {chatRoomId !== null && <ChatMessagesContent chatRoomId={chatRoomId}/>}
      </div>
      {chatRoomId !== null && <ChatUserSidebar chatRoomId={chatRoomId} />}
    </>
  )
}
