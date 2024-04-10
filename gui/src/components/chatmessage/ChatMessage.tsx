import {ChatMessage as ChatMessageType} from "@/graphql/types.ts";
import {css} from "@emotion/react";
import {HStack} from "@/lib/style/layouts.tsx";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";

const frameStyle = css`
    margin: 1.3rem;
`;

const contentStyle = css`
    margin-left: 0.5rem;
`;

const nicknameStyle = css`
    font-weight: 600;
`;

const messageStyle = css`
    margin-top: 0.2rem;
`;

interface ChatMessageProps {
  chatMessage: ChatMessageType;
}

export function ChatMessage({ chatMessage }: ChatMessageProps) {

  return (
    <HStack css={frameStyle}>
      <button>
        <img
          src={`${consts.endpoint}${chatMessage.createdBy.avatarUrl}`}
          css={iconStyle}
          alt="sender-avatar"
        />
      </button>
      <div css={contentStyle}>
        <span css={nicknameStyle}>{chatMessage.createdBy.nickname}</span>
        <div css={messageStyle}>{chatMessage.content}</div>
      </div>
    </HStack>
  )
}
