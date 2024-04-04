import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {mq} from "@/lib/mediaQueryHelpers.ts";
import {css} from "@emotion/react";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";

const left = mq.m_all(2,2,3,3,3,3);
const right = mq.m_all(10,10,9, 9,9,9);

const containerStyle = css`
    display: flex;
    //flex-direction: row;
    //justify-content: flex-start;
    //align-items: stretch;
    height: 100vh;
    //margin: 0;
    //padding: 0;
`;

const flexStyle = css`
    display: flex;
    //justify-content: flex-start;
`;

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
      <div css={[right, flexStyle]}>
        <ChatRoomContent chatRoomId={chatRoomId}/>
      </div>
    </div>
  )
}
