import {Account, ChatUser} from "@/graphql/types.ts";
import {useChatMessagesScroll} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {useChatMessagesRTC} from "@/hooks/chatmessage/useChatMessagesRTC.ts";

export function useChatMessages(
  chatRoomId: number, myInfo: Account, chatUsers: ChatUser[],
) {

  const {
    chatMessages, setChatMessages,
    page, loading,
    scrollRef, observerRef,
    setOffset, setScrollType
  } = useChatMessagesScroll(chatRoomId);

  // const {connect, disconnect, send} = useChatMessagesStomp(chatRoomId, setChatMessages, setOffset, setScrollType);
  const {
    connect, disconnect, send,
  } = useChatMessagesRTC(chatRoomId, myInfo, chatUsers, setChatMessages, setOffset, setScrollType);

  return {
    chatMessages, page, observerRef, scrollRef, loading,
    connect, disconnect, send,
  };
}
