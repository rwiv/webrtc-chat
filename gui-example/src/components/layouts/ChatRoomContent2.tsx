import {ChatUserSidebar} from "@/components/chatuser/ChatUserSidebar.tsx";
import {ChatMessagesContent} from "@/components/chatmessage/ChatMessagesContent.tsx";
import {css} from "@emotion/react";
import {ChatRoomContentHeader} from "@/components/layouts/ChatRoomContentHeader.tsx";
import {mq} from "@/lib/mediaQueryHelpers.ts";

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

const left = mq.m_all(10,10,9, 9,9,9);
const right = mq.m_all(2,2,3,3,3,3);

export function ChatRoomContent2({ chatRoomId }: ChatRoomContentProps) {

  return (
    <>
      <div css={[left, columnStyle]}>
        <ChatRoomContentHeader chatRoomId={chatRoomId} />
        {chatRoomId !== null && <ChatMessagesContent chatRoomId={chatRoomId}/>}
      </div>
      <div css={[right]}>
        {chatRoomId !== null && <ChatUserSidebar chatRoomId={chatRoomId} />}
      </div>
    </>
  )
}
