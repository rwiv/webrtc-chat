import {ChatUserSidebar} from "@/components/chatuser/ChatUserSidebar.tsx";
import {ChatMessagesContent} from "@/components/chatmessage/ChatMessagesContent.tsx";
import {css} from "@emotion/react";
import {ChatRoomContentHeader} from "@/components/layouts/ChatRoomContentHeader.tsx";
import {mq} from "@/lib/style/mediaQueries.ts";

const mainContentStyle = css`
    //flex-grow: 1;
    display: flex;
    flex-direction: column;
    background-color: #eeeeee;
    border-right: 2px solid #e2e2e2;
`;

const sidebarStyle = css`
    display: flex;
    flex-direction: column;
    background-color: #eeeeee;
`;

interface ChatRoomContentProps {
  chatRoomId: number | null;
}

const left = mq.m_all(10,10,9, 9,9,9);
const right = mq.m_all(2,2,3,3,3,3);

export function ChatRoomContent({ chatRoomId }: ChatRoomContentProps) {

  return (
    <>
      <div css={[left, mainContentStyle]}>
        <ChatRoomContentHeader chatRoomId={chatRoomId} />
        {chatRoomId !== null && <ChatMessagesContent chatRoomId={chatRoomId}/>}
      </div>
      <div css={[right, sidebarStyle]}>
        {chatRoomId !== null && <ChatUserSidebar chatRoomId={chatRoomId} />}
      </div>
    </>
  )
}
