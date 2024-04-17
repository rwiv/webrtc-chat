import {ChatUserSidebarList} from "@/components/chatuser/ChatUserSidebarList.tsx";
import {css} from "@emotion/react";
import {ChatUser} from "@/graphql/types.ts";
import {HStack} from "@/lib/style/layouts.tsx";
import {InviteChatUserButton} from "@/components/chatuser/InviteChatUserButton.tsx";
import {rightAlignStyle} from "@/styles/globalStyles.ts";
import {useMyInfo} from "@/hooks/common/useMyInfo.ts";

const headerStyle = css`
  border-bottom: 2px solid #e2e2e2;
  //padding-top: 15px;
`;

const nameStyle = css`
  font-size: 1.4rem;
  font-weight: 600;
  margin-left: 1rem;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  font-family: 'Noto Sans KR'
`;

interface ChatUserSidebarProps {
  chatUsers: ChatUser[];
}

export function ChatUserSidebar({ chatUsers }: ChatUserSidebarProps) {

  const {myInfo} = useMyInfo();

  return (
    <div>
      <div css={headerStyle}>
        <HStack>
          <label css={nameStyle}>유저</label>
          <div css={rightAlignStyle}>
            <InviteChatUserButton chatUsers={chatUsers} />
          </div>
        </HStack>
      </div>
      {myInfo && (
        <ChatUserSidebarList chatUsers={chatUsers} myInfo={myInfo} />
      )}
    </div>
  )
}
