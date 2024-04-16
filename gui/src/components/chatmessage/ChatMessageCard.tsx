import {ChatMessage as ChatMessageType} from "@/graphql/types.ts";
import {css} from "@emotion/react";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";

const frameStyle = css`
  display: flex;
  flex-direction: row;
  margin: 1.3rem;
`;

const avatarStyle = css`
  min-width: 9%;
`;

const contentStyle = css`
  max-width: 90%;
  overflow-x: auto;
`;

const nicknameStyle = css`
  font-weight: 600;
  font-size: 1.15rem;
`;

const messageStyle = css`
  margin-top: 0.2rem;
  font-size: 1.1rem;
`;

interface ChatMessageProps {
  chatMessage: ChatMessageType;
}

export function ChatMessageCard({ chatMessage }: ChatMessageProps) {

  return (
    <div css={frameStyle}>
      <div css={avatarStyle}>
        <button>
          <img
            src={`${consts.endpoint}${chatMessage.createdBy.avatarUrl}`}
            css={iconStyle}
            alt="sender-avatar"
          />
        </button>
      </div>
      <div css={contentStyle}>
        <span css={nicknameStyle}>{chatMessage.createdBy.nickname}</span>
        <div css={messageStyle}>{chatMessage.content}</div>
      </div>
    </div>
  )
}
