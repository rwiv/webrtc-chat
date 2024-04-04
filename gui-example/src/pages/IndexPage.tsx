import {mq} from "@/lib/mediaQueryHelpers.ts";
import {css} from "@emotion/react";
import {LeftSidebar} from "@/components/layouts/LeftSidebar.tsx";
import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";

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
