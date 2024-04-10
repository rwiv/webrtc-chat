import {useEffect, useState} from "react";
import {ChatMessage} from "@/graphql/types.ts";
import {useChatMessagesScroll} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {useChatMessagesServerBasedStomp} from "@/hooks/chatmessage/useChatMessagesServerBasedStomp.ts";

export function useChatMessagesServerBased(chatRoomId: number) {

  const [chatMessages, setChatMessages] = useState<ChatMessage[]>([])
  const [page, setPage] = useState(1);

  const {
    loading,
    scrollRef, observerRef,
    setOffset, setScrollType
  } = useChatMessagesScroll(
    chatRoomId, page, setPage, chatMessages, setChatMessages,
  );

  const {connect, disconnect} = useChatMessagesServerBasedStomp(
    chatRoomId, setChatMessages, setOffset, setScrollType,
  );

  useEffect(() => {
    connect();
    return () => {
      disconnect();
    }
  }, []);

  return {chatMessages, page, observerRef, scrollRef, loading};
}
