import {useEffect, useState} from "react";
import {Account, ChatMessage, ChatUser} from "@/graphql/types.ts";
import {useChatMessagesScroll} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {useChatMessagesRTC} from "@/hooks/chatmessage/useChatMessagesRTC.ts";

export function useChatMessages(
  chatRoomId: number, myInfo: Account, chatUsers: ChatUser[],
) {

  const [chatMessages, setChatMessages] = useState<ChatMessage[]>([])
  const [page, setPage] = useState(1);

  const {
    // loading: pageLoading,
    scrollRef, observerRef,
    setOffset, setScrollType
  } = useChatMessagesScroll(
    chatRoomId, page, setPage, chatMessages, setChatMessages,
  );

  // const {connect, disconnect, send} = useChatMessagesStomp(chatRoomId, setChatMessages, setOffset, setScrollType);
  const {
    loading: rtcLoading,
    connect, disconnect, send
  } = useChatMessagesRTC(chatRoomId, myInfo, chatUsers, setChatMessages, setOffset, setScrollType);

  useEffect(() => {
    connect();
    return () => {
      disconnect();
    }
  }, []);

  return {chatMessages, send, page, observerRef, scrollRef, loading: rtcLoading};
}
