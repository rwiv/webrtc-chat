import {ChatMessageCard} from "@/components/chatmessage/ChatMessageCard.tsx";
import {css} from "@emotion/react";
import React from "react";
import {ChatMessage} from "@/graphql/types.ts";

const frameStyle = css`
    //padding: 1rem;
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

const SCROLL_RATIO = 0.7;

interface ChatMessageListProps {
  chatMessages: ChatMessage[];
  observerRef: React.RefObject<HTMLDivElement>;
  scrollRef: React.RefObject<HTMLDivElement>;
}

export function ChatMessageList({ chatMessages, observerRef, scrollRef }: ChatMessageListProps) {
  return (
    <div
      className="overflow-y-auto"
      css={[frameStyle, {height: window.innerHeight * SCROLL_RATIO}]}
      ref={scrollRef}
    >
      {(chatMessages.length > 0) && (
        <div ref={observerRef} css={css({height: 1})}/>
      )}
      {chatMessages?.map(chatMessage => (
        <ChatMessageCard key={chatMessage.id} chatMessage={chatMessage}/>
      ))}
    </div>
  )
}
