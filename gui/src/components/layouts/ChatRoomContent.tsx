import {ChatUserSidebar} from "@/components/chatuser/ChatUserSidebar.tsx";
import {ChatMessagesContent} from "@/components/chatmessage/ChatMessagesContent.tsx";
import {css} from "@emotion/react";
import {ChatRoomContentHeader} from "@/components/layouts/ChatRoomContentHeader.tsx";
import {mq} from "@/lib/style/mediaQueries.ts";
import {useMyInfo} from "@/hooks/common/useMyInfo.ts";
import {useChatRoomAndUsers} from "@/client/chatUser.ts";

const mainContentStyle = css`
  display: flex;
  flex-direction: column;
  border-right: 2px solid #e2e2e2;
`;

const sidebarStyle = css`
  display: flex;
  flex-direction: column;
`;

interface ChatRoomContentProps {
  chatRoomId: number;
}

const left = mq.m_all(12,8,8, 9,9,9);
const right = mq.m_all(0,4,4,3,3,3);

export function ChatRoomContent({ chatRoomId }: ChatRoomContentProps) {

  const {myInfo} = useMyInfo();
  const {data: usersData} = useChatRoomAndUsers(chatRoomId);
  const chatUsers = usersData?.chatRoom?.chatUsers ?? undefined;

  return (
    <>
      <div css={[left, mainContentStyle]}>
        <ChatRoomContentHeader />
        {myInfo !== undefined && chatUsers !== undefined && (
          <ChatMessagesContent chatRoomId={chatRoomId} myInfo={myInfo} chatUsers={chatUsers} />)
        }
      </div>
      <div css={[right, sidebarStyle]}>
        {chatUsers !== undefined &&
          (<ChatUserSidebar chatUsers={chatUsers} />)
        }
      </div>
    </>
  )
}
