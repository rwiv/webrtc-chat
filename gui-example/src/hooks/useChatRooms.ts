import {useApolloClient} from "@apollo/client";
import {useEffect, useState} from "react";
import {ChatRoom, Query} from "@/graphql/types.ts";
import {chatRoomsQL, defaultChatRoomSize} from "@/client/chatRoom.ts";
import {useIntersect} from "@/hooks/useIntersect.ts";
import type {QueryOptions} from "@apollo/client/core/watchQueryOptions";

export function useChatRooms() {

  const client = useApolloClient();
  const [page, setPage] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);
  const [loading, setLoading] = useState(false);
  const [chatRooms, setChatRooms] = useState<ChatRoom[]>([])

  function getQueryOptions(): QueryOptions {
    return {
      query: chatRoomsQL,
      variables: { page, size: defaultChatRoomSize },
      fetchPolicy: "network-only",
    }
  }

  useEffect(() => {
    client.query<Query>(getQueryOptions()).then(result => {
      setChatRooms(result.data.chatRooms ?? []);
    });
  }, []);

  useEffect(() => {
    if (page === 1) {
      return;
    }
    console.log(page)
    client.query<Query>(getQueryOptions()).then(result => {
      console.log("fetch");
      const chatRooms = result.data.chatRooms;
      if (chatRooms?.length === 0) {
        console.log("end");
        setHasNextPage(false);
        setLoading(false);
        return;
      }
      if (chatRooms === undefined || chatRooms === null) return;
      setChatRooms(prev => [...prev, ...chatRooms]);
      setLoading(false);
    });
  }, [page]);

  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target)
    if (hasNextPage && !loading) {
      setPage(prev => prev + 1);
      setLoading(true);
    }
  }, { threshold: 0 });

  const addChatRoom = (chatRoom: ChatRoom) => {
    setChatRooms(prev => [chatRoom, ...prev]);
  }

  const removeChatRoom = (chatRoomId: number) => {
    setChatRooms(prev => prev.filter(it => it.id !== chatRoomId));
  }

  return {chatRooms, ref, addChatRoom, removeChatRoom};
}
