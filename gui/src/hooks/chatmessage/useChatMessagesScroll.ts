import {useApolloClient} from "@apollo/client";
import React, {useCallback, useEffect, useRef, useState} from "react";
import {ChatMessage, Query} from "@/graphql/types.ts";
import {chatMessagesQL, defaultChatMessageSize} from "@/client/chatMessage.ts";
import {reverse} from "@/lib/misc/array.ts";
import type {QueryOptions} from "@apollo/client/core/watchQueryOptions";
import {useIntersect} from "@/hooks/common/useIntersect.ts";

export type ScrollType = "BOTTOM" | "TOP";

export function useChatMessagesScroll(
  chatRoomId: number,
  page: number,
  setPage: React.Dispatch<React.SetStateAction<number>>,
  chatMessages: ChatMessage[],
  setChatMessages: React.Dispatch<React.SetStateAction<ChatMessage[]>>,
) {

  const apolloClient = useApolloClient();
  const [hasNextPage, setHasNextPage] = useState(true);
  const [offset, setOffset] = useState(0);
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

  const getQueryOptions = useCallback((): QueryOptions => {
    return {
      query: chatMessagesQL,
      variables: { chatRoomId, page, size: defaultChatMessageSize, offset},
      fetchPolicy: "network-only",
    }
  }, [chatRoomId, offset, page]);

  useEffect(() => {
    // init request
    apolloClient.query<Query>(getQueryOptions()).then(result => {
      const messages = result?.data?.chatMessages;
      setChatMessages(reverse(messages) ?? []);
    });
  }, []);

  useEffect(() => {
    if (page === 1) {
      return;
    }

    setScrollHeight(scrollRef.current?.scrollHeight ?? 0);

    apolloClient.query<Query>(getQueryOptions()).then(result => {
      const chatMessages = result.data.chatMessages;
      if (chatMessages?.length === 0) {
        console.log("end");
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

  return {loading, scrollRef, observerRef, setOffset, setScrollType};
}
