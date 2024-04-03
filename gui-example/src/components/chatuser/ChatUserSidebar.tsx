import {ChatUserSidebarList} from "@/components/chatuser/ChatUserSidebarList.tsx";
import {css} from "@emotion/react";

const columnStyle = css`
    width: 17.5%;
    background-color: #eeeeee;
    display: flex;
    flex-direction: column;
`;

const headerStyle = css`
    height: 7.5%;
    justify-content: flex-start;
    border-bottom: 2px solid #e2e2e2;
    padding-top: 15px;
`;

const nameStyle = css`
    font-size: 27px;
    padding-left: 25px;
    font-family: 'Noto Sans KR'
`;

interface ChatUserSidebarProps {
  chatRoomId: number;
}

export function ChatUserSidebar({ chatRoomId }: ChatUserSidebarProps) {

  return (
    <div css={columnStyle}>
      <div css={headerStyle}>
        <label css={nameStyle}>유저</label>
      </div>
      <ChatUserSidebarList chatRoomId={chatRoomId} />
    </div>
  )
}
