import {useApolloClient} from "@apollo/client";
import {useEffect, useRef, useState} from "react";
import {ChatMessage, Query} from "@/graphql/types.ts";
import {useIntersect} from "@/hooks/useIntersect.ts";
import {chatMessageQL, chatMessagesQL, defaultChatMessageSize} from "@/client/chatMessage.ts";
import {Client, IMessage, StompSubscription} from "@stomp/stompjs";
import {consts} from "@/configures/consts.ts";
import type {QueryOptions} from "@apollo/client/core/watchQueryOptions";
import {reverse} from "@/lib/array.ts";

export function useChatMessages(chatRoomId: number) {

  const apolloClient = useApolloClient();
  const [page, setPage] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);
  const [loading, setLoading] = useState(false);
  const [chatMessages, setChatMessages] = useState<ChatMessage[]>([])
  const [offset, setOffset] = useState(0);
  const [reqSize, setReqSize] = useState(defaultChatMessageSize);

  const scrollRef = useRef<HTMLDivElement>(null)
  const [scrollType, setScrollType] = useState<"BOTTOM" | "TOP">("BOTTOM");

  const [stompClient, setStompClient] = useState<Client>();
  const [stompSubs, setStompSubs] = useState<StompSubscription[]>([]);

  // stomp functions
  async function subscribe(msg: IMessage) {
    const body = JSON.parse(msg.body) as { id: number, num: number };
    const res = await apolloClient.query<Query>({
      query: chatMessageQL, variables: { id: body.id }, fetchPolicy: "network-only",
    });
    const chatMessage = res.data.chatMessage;
    if (chatMessage === undefined || chatMessage === null) return;

    setChatMessages(prev => {
      if (prev.find(it => it.id === body.id) !== undefined) {
        return prev;
      }
      return [...prev, chatMessage];
    });
    setOffset(prev => prev + 1);
    setScrollType("BOTTOM");
  }

  function connect() {
    console.log("connect");
    if (stompClient !== undefined) {
      return;
    }
    const dest = `/sub/message/${chatRoomId}`;
    const newStompClient = new Client({
      brokerURL: `ws://${consts.domain}/stomp`,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        const sub = newStompClient.subscribe(dest, subscribe);
        setStompSubs(prev => {
          prev.push(sub);
          return prev;
        });
      }
    });
    newStompClient.activate();
    setStompClient(newStompClient);
  }

  function disconnect() {
    console.log("disconnect");

    stompSubs.forEach(it => {
      it.unsubscribe();
    });

    stompClient?.deactivate();
    setStompClient(undefined);
  }

  // infinite scroll
  function getQueryOptions(): QueryOptions {
    return {
      query: chatMessagesQL,
      variables: { chatRoomId, page, size: defaultChatMessageSize, offset: offset / 2 },
      fetchPolicy: "network-only",
    }
  }

  useEffect(() => {
    // init request
    apolloClient.query<Query>(getQueryOptions()).then(result => {
      const messages = result?.data?.chatMessages;
      setChatMessages(reverse(messages) ?? []);
    });

    // stomp connection handling
    connect();
    return disconnect;
  }, []);

  useEffect(() => {
    if (page === 1) {
      return;
    }
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

    const height = scrollRef.current?.scrollHeight ?? 0;
    const left = scrollRef.current?.scrollLeft ?? 0;

    const ch = scrollRef?.current?.querySelector(".child");
    const top = (ch?.scrollHeight ?? 0) * (reqSize + 2);

    if (scrollType === "BOTTOM") {
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
  }, { threshold: 0 })

  return {chatMessages, observerRef, scrollRef, loading};
}
