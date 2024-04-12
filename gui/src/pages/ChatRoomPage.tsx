import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {mq} from "@/lib/style/mediaQueries.ts";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";
import {containerStyle, flexStyle} from "@/styles/globalStyles.ts";

const left = mq.m_all(0, 0, 3, 3, 3, 3);
const right = mq.m_all(12, 12, 9, 9, 9, 9);

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
    <div css={containerStyle}>
      <div css={[left, flexStyle]}>
        <LeftSidebar/>
      </div>
      <div css={[right, flexStyle, {background: "#eeeeee"}]}>
        {chatRoomId !== null && (
          <ChatRoomContent chatRoomId={chatRoomId}/>
        )}
      </div>
    </div>
  )
}
