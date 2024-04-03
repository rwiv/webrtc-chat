import {useParams} from "react-router";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {LeftSideBar} from "@/components/layouts/LeftSideBar.tsx";
import {useEffect, useState} from "react";

export function ChatRoomPage() {

  const params = useParams();
  const [chatRoomId, setChatRoomId] = useState<number | null>(null);

  useEffect(() => {
    setChatRoomId(null);
  }, [params]);

  useEffect(() => {
    if (chatRoomId === null) {
      setChatRoomId(getChatRoomId());
    }
  }, [chatRoomId]);

  function getChatRoomId() {
    const chatRoomId = params["chatRoomId"];
    if (chatRoomId === undefined) {
      throw Error("chatRoomId is null");
    }
    const idNum = parseInt(chatRoomId);
    if (isNaN(idNum)) {
      throw Error("chatRoomId is NaN");
    }
    return idNum;
  }

  return (
    <div className="container">
      <LeftSideBar />
      <ChatRoomContent chatRoomId={chatRoomId} />
    </div>
  )
}
