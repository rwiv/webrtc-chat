import {useEffect, useRef, useState} from "react";
import {chatRoomsQL, defaultSize} from "@/client/chatRoom.ts";
import {useApolloClient} from "@apollo/client";
import {ChatRoom, Query} from "@/graphql/types.ts";
import {useIntersect} from "@/hooks/useIntersect.ts";


export function ChatRoomListReverse() {

  const scrollRef = useRef<HTMLDivElement>(null)
  const [top, setTop] = useState(0);

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
    setTop(scrollRef.current?.scrollTop ?? 0);
    setPage(prev => prev + 1);
    setLoading(true);
  };

  const observerRef = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target)
    if (hasNextPage && !loading) {
      await fetchNextPage();
    }
  }, { threshold: 0 })

  useEffect(() => {
    if (chatRooms.length === 0) return;

    const height = scrollRef.current?.scrollHeight ?? 0;
    const left = scrollRef.current?.scrollLeft ?? 0;

    if (chatRooms.length == defaultSize) {
      console.log(height)
      console.log(left)
      console.log(scrollRef.current?.scrollTop)
      scrollRef.current?.scrollTo({left, top: height});
      return;
    }
    scrollRef.current?.scrollTo({left, top: top + 100});
  }, [scrollRef, chatRooms]);

  return (
    <div className="h-24 w-48 overflow-y-auto" ref={scrollRef}>
      {(chatRooms.length > 0 && !loading) && (
        <div ref={observerRef} className="h-[1rem]"/>
      )}
      {chatRooms?.map(chatRoom => (
        <div key={chatRoom.id}>{chatRoom.title}</div>
      ))}
    </div>
  )
}
