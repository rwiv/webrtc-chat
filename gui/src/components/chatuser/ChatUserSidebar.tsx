import {ChatUserSidebarList} from "@/components/chatuser/ChatUserSidebarList.tsx";
import {css} from "@emotion/react";
import {ChatUser} from "@/graphql/types.ts";

const headerStyle = css`
    border-bottom: 2px solid #e2e2e2;
    padding-top: 15px;
`;

const nameStyle = css`
    font-size: 27px;
    padding-left: 25px;
    font-family: 'Noto Sans KR'
`;

interface ChatUserSidebarProps {
  chatUsers: ChatUser[];
}

export function ChatUserSidebar({ chatUsers }: ChatUserSidebarProps) {

  return (
    <div>
      <div css={headerStyle}>
        <label css={nameStyle}>유저</label>
      </div>
      <ChatUserSidebarList chatUsers={chatUsers} />
    </div>
  )
}
