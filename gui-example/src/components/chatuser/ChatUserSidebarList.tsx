import {useChatRoomAndUsers} from "@/client/chatUser.ts";
import {css} from "@emotion/react";
import {ChatUser} from "@/graphql/types.ts";
import Avatar from "@/imgs/avatar3.svg";

const mainStyle = css`
    flex-grow: 1;
`;

interface ChatUserSidebarListProps {
  chatRoomId: number;
}

export function ChatUserSidebarList({ chatRoomId }: ChatUserSidebarListProps) {

  const {data: users} = useChatRoomAndUsers(chatRoomId);

  return (
    <div css={mainStyle}>
      {users?.chatRoom?.chatUsers?.map(chatUser => (
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

const iconStyle = css`
    border-radius: 50%;
    width: 2.3rem;
    height: 2.3rem;
`;

interface ChatUserItemProps {
  chatUser: ChatUser;
}

function ChatUserItem({ chatUser }: ChatUserItemProps) {
  return (
    <div css={itemStyle}>
      <button>
        <img src={Avatar} css={iconStyle} alt="my-avatar"></img>
      </button>
      <div css={{marginLeft: "0.7rem"}}>
        <div className="font-semibold">{chatUser.account.nickname}</div>
        <div>{chatUser.account.username}</div>
      </div>
    </div>
  )
}
