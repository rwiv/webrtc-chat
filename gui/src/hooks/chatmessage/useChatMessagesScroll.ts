import {useApolloClient} from "@apollo/client";
import React, {useEffect, useRef, useState} from "react";
import {ChatMessage, Query} from "@/graphql/types.ts";
import {chatMessagesQL, defaultChatMessageSize} from "@/client/chatMessage.ts";
import {reverse} from "@/lib/misc/array.ts";
import type {QueryOptions} from "@apollo/client/core/watchQueryOptions";
import {getMargin} from "@/lib/style/css_utils.ts";
import {useIntersect} from "@/hooks/useIntersect.ts";

export function useChatMessagesScroll(
  chatRoomId: number,
  page: number,
  setPage: React.Dispatch<React.SetStateAction<number>>,
  loading: boolean,
  setLoading: React.Dispatch<React.SetStateAction<boolean>>,
  chatMessages: ChatMessage[],
  setChatMessages: React.Dispatch<React.SetStateAction<ChatMessage[]>>,
) {

  const apolloClient = useApolloClient();
  const [hasNextPage, setHasNextPage] = useState(true);
  const [offset, setOffset] = useState(0);
  const [reqSize, setReqSize] = useState(defaultChatMessageSize);

  const scrollRef = useRef<HTMLDivElement>(null)
  const [scrollType, setScrollType] = useState<"BOTTOM" | "TOP">("BOTTOM");
  const [scrollPosition, setScrollPosition] = useState(0);

  function getQueryOptions(): QueryOptions {
    return {
      query: chatMessagesQL,
      variables: { chatRoomId, page, size: defaultChatMessageSize, offset: offset },
      fetchPolicy: "network-only",
    }
  }

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

    setScrollPosition(scrollRef.current?.scrollTop ?? 0);

    apolloClient.query<Query>(getQueryOptions()).then(result => {
      const chatMessages = result.data.chatMessages;
      if (chatMessages?.length === 0) {
        console.log("end");
        setHasNextPage(false);
        setLoading(false);
        return;
      }
      setReqSize(chatMessages?.length ?? defaultChatMessageSize);
      setChatMessages(prev => [...reverse(chatMessages), ...prev]);
      setLoading(false);
      setScrollType("TOP");
    });
  }, [page]);

  useEffect(() => {
    if (chatMessages.length === 0) return;

    const left = scrollRef.current?.scrollLeft ?? 0;

    const child = scrollRef?.current?.querySelector(".child");
    const childHeight = (child?.scrollHeight ?? 0) + (getMargin(child) ?? 0);
    const top = scrollPosition + (childHeight * reqSize) + 5;

    if (scrollType === "BOTTOM") {
      const height = scrollRef.current?.scrollHeight ?? 0;
      scrollRef.current?.scrollTo({left, top: height});
    }
    if (scrollType === "TOP") {
      scrollRef.current?.scrollTo({left, top});
    }
  }, [chatMessages]);

  const observerRef = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target)
    if (hasNextPage && !loading) {
      setPage(prev => prev + 1);
      setLoading(true);
    }
  }, { threshold: 0 });

  return {scrollRef, observerRef, setOffset, setScrollType};
}
