import {mq} from "@/lib/style/mediaQueries.ts";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {containerStyle, flexStyle} from "@/styles/globalStyles.ts";
import {useEffect} from "react";
import {useCurChatRoomStore} from "@/hooks/chatroom/useCurChatRoomStore.ts";
import {useSidebarStateStore} from "@/hooks/common/useSidebarStateStore.ts";

const left = mq.m_all(2,2,3,3,3,3);
const right = mq.m_all(10,10,9, 9,9,9);

export function IndexPage() {

  const {setCurChatRoom} = useCurChatRoomStore();
  const {setSidebarState} = useSidebarStateStore();

  useEffect(() => {
    setCurChatRoom(null);
    setSidebarState("CHATROOM");
  }, []);

  return (
    <div css={containerStyle}>
      <div css={[left, flexStyle]}>
        <LeftSidebar/>
      </div>
      <div css={[right, flexStyle, {background: "#eeeeee"}]}>
        <ChatRoomContent chatRoomId={-1}/>
      </div>
    </div>
  )
}
