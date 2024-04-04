import {ChatRoomContent} from "@/components/layouts/ChatRoomContent.tsx";
import {css} from "@emotion/react";
import {LeftSideBar2} from "@/components/layouts/LeftSidebar2.tsx";
import {mq} from "@/lib/mediaQueryHelpers.ts";

const left = mq.m_all(1,1,2,2,2,2);
// const right = mq.m_all(11,11,10, 10,10,10);

const containerStyle = css`
    display: flex;
    //flex-direction: row;
    //justify-content: flex-start;
    //align-items: stretch;
    height: 100vh;
    margin: 0;
    padding: 0;
`;

const flexStyle = css`
    display: flex;
    //justify-content: flex-start;
`;

export function TestPage() {
  return (
    <div css={containerStyle}>
      <div css={[left, flexStyle]}>
        <LeftSideBar2/>
      </div>
      <ChatRoomContent chatRoomId={1}/>
      {/*<div css={[right, flexStyle]}>*/}
      {/*  <ChatRoomContent2 chatRoomId={1}/>*/}
      {/*</div>*/}
    </div>
  )
}
