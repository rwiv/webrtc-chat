import {useApolloClient} from "@apollo/client";
import {useEffect, useState} from "react";
import {ChatRoom, Query} from "@/graphql/types.ts";
import {chatRoomsQL, defaultSize} from "@/client/chatRoom.ts";
import {useIntersect} from "@/hooks/useIntersect.ts";

export function useChatRoomsReverse() {

  const client = useApolloClient();
  const [page, setPage] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);
  const [loading, setLoading] = useState(false);
  const [chatRooms, setChatRooms] = useState<ChatRoom[]>([])

  useEffect(() => {
    if (page === 1) return;
    console.log(page)
    client.query<Query>({
      query: chatRoomsQL,
      variables: { page, size: defaultSize },
      fetchPolicy: "network-only",
    }).then(result => {
      console.log("fetch");
      const chatRooms = result.data.chatRooms;
      if (chatRooms?.length === 0) {
        console.log("end");
        setHasNextPage(false);
        setLoading(false);
        return;
      }
      setChatRooms(prev => {
        const ret: ChatRoom[] = [];
        chatRooms?.forEach(it => ret.push(it));
        prev?.forEach(it => ret.push(it));
        return ret;
      });
      setLoading(false);
    });
  }, [page]);

  useEffect(() => {
    client.query<Query>({
      query: chatRoomsQL,
      variables: { page, size: defaultSize },
      fetchPolicy: "network-only",
    }).then(result => {
      setChatRooms(result.data.chatRooms ?? []);
    });
  }, []);

  const fetchNextPage = async () => {
    setPage(prev => prev + 1);
    setLoading(true);
  };

  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target)
    if (hasNextPage && !loading) {
      await fetchNextPage();
    }
  }, { threshold: 0 })

  return {chatRooms, ref};
}
