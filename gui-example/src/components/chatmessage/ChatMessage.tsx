import {ChatMessage as ChatMessageType} from "@/graphql/types.ts";
import Avatar from "@/imgs/avatar5.svg";
import {css} from "@emotion/react";
import {HStack} from "@/lib/layouts.tsx";

const frameStyle = css`
  margin: 1.3rem;
`;

const iconStyle = css`
    border-radius: 50%;
    width: 2.3rem;
    height: 2.3rem;
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
    <HStack className="child" css={frameStyle}>
      <button>
        <img src={Avatar} css={iconStyle} alt="my-avatar"></img>
      </button>
      <div css={contentStyle}>
        <span css={nicknameStyle}>{chatMessage.createdBy.nickname}</span>
        <div css={messageStyle}>{chatMessage.content}</div>
      </div>
    </HStack>
  )
}
