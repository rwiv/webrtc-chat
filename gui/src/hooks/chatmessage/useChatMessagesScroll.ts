import {useApolloClient} from "@apollo/client";
import {useCallback, useEffect, useRef, useState} from "react";
import {ChatMessage, Query} from "@/graphql/types.ts";
import {chatMessagesQL, defaultChatMessageSize} from "@/client/chatMessage.ts";
import {reverse} from "@/lib/common/array.ts";
import type {QueryOptions} from "@apollo/client/core/watchQueryOptions";
import {useIntersect} from "@/hooks/common/useIntersect.ts";
import {useChatMessagesRefreshStore} from "@/hooks/chatmessage/useChatMessagesRefreshStore.ts";

export type ScrollType = "BOTTOM" | "TOP";

const initPage = 1;
const initOffset = 0;

export function useChatMessagesScroll(chatRoomId: number) {

  const apolloClient = useApolloClient();

  const {refreshFlag} = useChatMessagesRefreshStore();

  const [chatMessages, setChatMessages] = useState<ChatMessage[]>([])

  const [page, setPage] = useState(initPage);
  const [offset, setOffset] = useState(initOffset);
  const [hasNextPage, setHasNextPage] = useState(true);

  const [loading, setLoading] = useState(false);

  const scrollRef = useRef<HTMLDivElement>(null)
  const [scrollType, setScrollType] = useState<ScrollType>("BOTTOM");
  const [scrollHeight, setScrollHeight] = useState(0);

  const observerRef = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target)
    if (hasNextPage && !loading) {
      setPage(prev => prev + 1);
      setLoading(true);
    }
  }, { threshold: 0 });

  const getQueryOptions = useCallback((page: number, offset: number): QueryOptions => {
    return {
      query: chatMessagesQL,
      variables: { chatRoomId, page, size: defaultChatMessageSize, offset},
      fetchPolicy: "network-only",
    }
  }, [chatRoomId, offset, page]);

  useEffect(() => {
    setPage(initPage);
    setOffset(initOffset);
    // init request
    apolloClient.query<Query>(getQueryOptions(initPage, initOffset)).then(result => {
      const messages = result?.data?.chatMessages;
      setChatMessages(reverse(messages) ?? []);
    });
  }, [refreshFlag]);

  useEffect(() => {
    if (page === 1) {
      return;
    }

    setScrollHeight(scrollRef.current?.scrollHeight ?? 0);

    apolloClient.query<Query>(getQueryOptions(page, offset)).then(result => {
      const chatMessages = result.data.chatMessages;
      if (chatMessages?.length === 0) {
        console.log("chatMessage page end");
        setHasNextPage(false);
        setLoading(false);
        return;
      }
      setChatMessages(prev => [...reverse(chatMessages), ...prev]);
      setLoading(false);
      setScrollType("TOP");
    });
  }, [page]);

  useEffect(() => {
    if (chatMessages.length === 0) return;

    const left = scrollRef.current?.scrollLeft ?? 0;

    if (scrollType === "BOTTOM") {
      const height = scrollRef.current?.scrollHeight ?? 0;
      scrollRef.current?.scrollTo({left, top: height});
    }
    if (scrollType === "TOP") {
      const top = (scrollRef.current?.scrollHeight ?? 0) - scrollHeight;
      scrollRef.current?.scrollTo({left, top});
    }
    setScrollType("BOTTOM");
  }, [chatMessages]);

  return {chatMessages, setChatMessages, page, loading, scrollRef, observerRef, setOffset, setScrollType};
}
