import {mq} from "@/lib/mediaQueryHelpers.ts";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {containerStyle, flexStyle} from "@/styles/globalStyles.ts";

const left = mq.m_all(2,2,3,3,3,3);
const right = mq.m_all(10,10,9, 9,9,9);

export default function IndexPage() {
  return (
    <div css={containerStyle}>
      <div css={[left, flexStyle]}>
        <LeftSidebar/>
      </div>
      <div css={[right, flexStyle]}>
        <ChatRoomContent chatRoomId={null}/>
      </div>
    </div>
  )
}
