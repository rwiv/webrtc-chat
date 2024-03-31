import {useChatMessages} from "@/hooks/useChatMessages.ts";
import {ChatMessage} from "@/components/chatmessage/ChatMessage.tsx";
import {css} from "@emotion/react";

interface ChatMessageListProps {
  chatRoomId: number;
}

export function ChatMessageList({ chatRoomId }: ChatMessageListProps) {

  const {
    chatMessages, observerRef, scrollRef, loading
  } = useChatMessages(chatRoomId);

  return (
    <div
      className="overflow-y-auto"
      css={css({height: "20rem", width: "20rem"})}
      ref={scrollRef}
    >
      {(chatMessages.length > 0 && !loading) && (
        <div ref={observerRef} css={css({height: 0})} />
      )}
      {chatMessages?.map(chatMessage => (
        <ChatMessage key={chatMessage.id} chatMessage={chatMessage}/>
      ))}
    </div>
  )
}
