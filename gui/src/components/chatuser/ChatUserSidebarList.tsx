import {css} from "@emotion/react";
import {ChatUser} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";
import {useDccMapStore} from "@/hooks/chatmessage/useDccMapStore.ts";
import {useEffect} from "react";
import {HStack} from "@/lib/style/layouts.tsx";

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

  const {dccMap, refresh: refreshDccMap} = useDccMapStore();

  const isConnected = (chatUser: ChatUser) => {
    const targetId = chatUser.account.id;
    return dccMap.get(targetId)?.isConnected() ?? false;
  }

  useEffect(() => {
    const interval = setInterval(() => {
      refreshDccMap();
    }, 1000);

    return () => {
      clearInterval(interval);
    }
  }, []);

  return (
    <div css={mainStyle}>
      {chatUsers?.map(chatUser => (
        <ChatUserItem
          key={chatUser.id}
          chatUser={chatUser}
          isConnected={isConnected(chatUser)}
        />
      ))}
    </div>
  )
}

const itemStyle = css`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 1.3rem 1rem;
`;

const avatarStyle = css`
  min-width: 7%;
`;

const contentStyle = css`
  margin-left: 1rem;
  max-width: 70%;
  overflow-x: auto;
`;

interface ChatUserItemProps {
  chatUser: ChatUser;
  isConnected: boolean;
}

function ChatUserItem({ chatUser, isConnected }: ChatUserItemProps) {
  return (
    <div css={itemStyle}>
      <div css={avatarStyle}>
        <button>
          <img
            src={`${consts.endpoint}${chatUser.account.avatarUrl}`}
            css={iconStyle}
            alt="chat-user-avatar"
          />
        </button>
      </div>
      <div css={contentStyle}>
        <HStack>
          <div className="font-semibold">{chatUser.account.nickname}</div>
          {isConnected ? (
            <div className="self-center rounded-full bg-green-500 h-1.5 w-1.5"></div>
          ) : (
            <div className="self-center rounded-full bg-red-500 h-1.5 w-1.5"></div>
          )}
        </HStack>
        <div>{chatUser.account.username}</div>
      </div>
    </div>
  )
}
