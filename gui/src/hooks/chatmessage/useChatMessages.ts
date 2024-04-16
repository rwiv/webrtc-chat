import {Account, ChatUser} from "@/graphql/types.ts";
import {useChatMessagesScroll} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {useChatMessagesRTC} from "@/hooks/chatmessage/useChatMessagesRTC.ts";

export function useChatMessages(
  chatRoomId: number, myInfo: Account, chatUsers: ChatUser[],
) {

  const {
    // loading: pageLoading,
    chatMessages, setChatMessages, page,
    scrollRef, observerRef,
    setOffset, setScrollType
  } = useChatMessagesScroll(chatRoomId);

  // const {connect, disconnect, send} = useChatMessagesStomp(chatRoomId, setChatMessages, setOffset, setScrollType);
  const {
    loading: rtcLoading,
    connect, disconnect, send,
  } = useChatMessagesRTC(chatRoomId, myInfo, chatUsers, setChatMessages, setOffset, setScrollType);

  return {
    chatMessages, page, observerRef, scrollRef,
    connect, disconnect, send,
    loading: rtcLoading,
  };
}
