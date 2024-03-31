import {ChatMessage as ChatMessageType} from "@/graphql/types.ts";

interface ChatMessageProps {
  chatMessage: ChatMessageType;
}

export function ChatMessage({ chatMessage }: ChatMessageProps) {

  return (
    <div className="m-2 child">
      <div className="font-semibold">{chatMessage.createAccount.nickname}</div>
      <div>{chatMessage.content}</div>
    </div>
  )
}
