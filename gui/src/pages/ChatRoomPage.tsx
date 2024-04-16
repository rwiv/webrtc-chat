import {useParams} from "react-router";
import {useEffect} from "react";
import {mq} from "@/lib/style/mediaQueries.ts";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";
import {containerStyle, flexStyle} from "@/styles/globalStyles.ts";
import {useChatMessagesRefreshStore} from "@/hooks/chatmessage/useChatMessagesRefreshStore.ts";

const left = mq.m_all(0, 0, 3, 3, 3, 3);
const right = mq.m_all(12, 12, 9, 9, 9, 9);

export function ChatRoomPage() {

  const params = useParams();
  const chatRoomId = getChatRoomId();

  const {refresh} = useChatMessagesRefreshStore();

  function getChatRoomId() {
    const chatRoomId = params["chatRoomId"];
    if (chatRoomId === undefined) {
      return null;
    }
    const idNum = parseInt(chatRoomId);
    if (isNaN(idNum)) {
      return null;
    }
    return idNum;
  }

  useEffect(() => {
    refresh();
  }, [params]);

  return (
    <div css={containerStyle}>
      {/*<DccMapTest params={params} />*/}
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
