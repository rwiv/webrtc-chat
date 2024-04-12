import {css} from "@emotion/react";
import {ChatUser} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";

const mainStyle = css`
  flex-grow: 1;
  overflow-y: auto;
  ::-webkit-scrollbar {
    width: 0.5rem;
  }
  ::-webkit-scrollbar-track {
    background: transparent;
  }
  ::-webkit-scrollbar-thumb {
    background: #000000;
    border-radius: 10px;
  }
  ::-webkit-scrollbar-thumb:hover {
    background: #555555;
  }
`;

interface ChatUserSidebarListProps {
  chatUsers: ChatUser[];
}

export function ChatUserSidebarList({ chatUsers }: ChatUserSidebarListProps) {

  return (
    <div css={mainStyle}>
      {chatUsers?.map(chatUser => (
        <ChatUserItem key={chatUser.id} chatUser={chatUser}/>
      ))}
    </div>
  )
}

const itemStyle = css`
  display: flex;
  align-items: center;
  padding: 10px;
  color: black;
`;

interface ChatUserItemProps {
  chatUser: ChatUser;
}

function ChatUserItem({ chatUser }: ChatUserItemProps) {
  return (
    <div css={itemStyle}>
      <button>
        <img
          src={`${consts.endpoint}${chatUser.account.avatarUrl}`}
          css={iconStyle}
          alt="chat-user-avatar"
        />
      </button>
      <div css={{marginLeft: "0.7rem"}}>
        <div className="font-semibold">{chatUser.account.nickname}</div>
        <div>{chatUser.account.username}</div>
      </div>
    </div>
  )
}
