import {Account, ChatUser} from "@/graphql/types.ts";
import {useChatMessagesScroll} from "@/hooks/chatmessage/useChatMessagesScroll.ts";
import {useChatMessagesRTC} from "@/hooks/chatmessage/useChatMessagesRTC.ts";
import {useChatMessagesRefreshStore} from "@/hooks/chatmessage/useChatMessagesRefreshStore.ts";
import {useEffect, useState} from "react";
import {useDccMapStore} from "@/hooks/chatmessage/useDccMapStore.ts";
import {useChatMessageStompStore} from "@/hooks/chatmessage/useChatMessageStompStore.ts";

export function useChatMessages(
  chatRoomId: number, myInfo: Account, chatUsers: ChatUser[],
) {

  const {refreshFlag} = useChatMessagesRefreshStore();
  const {dccMap} = useDccMapStore();
  const {stompClient} = useChatMessageStompStore();

  const [curInterval, setCurInterval] = useState<NodeJS.Timeout>();

  const {
    chatMessages, setChatMessages,
    page, loading,
    scrollRef, observerRef,
    setOffset, setScrollType
  } = useChatMessagesScroll(chatRoomId);

  const {
    connect, disconnect, send,
  } = useChatMessagesRTC(chatRoomId, myInfo, chatUsers, setChatMessages, setOffset, setScrollType);

  useEffect(() => {
    return () => {
      disconnect();
    }
  }, []);

  useEffect(() => {
    if (curInterval !== undefined)
      clearInterval(curInterval)

    disconnect();

    const itv = setInterval(() => {
      if (dccMap.isInit() && stompClient.isInit()) {
        clearInterval(itv);
        setCurInterval(undefined);
        connect();
      }
    }, 100);

    setCurInterval(itv);
  }, [refreshFlag]);

  return {
    chatMessages, page, observerRef, scrollRef, loading,
    connect, disconnect, send,
  };
}
