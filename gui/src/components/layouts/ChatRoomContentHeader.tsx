import {css} from "@emotion/react";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";

const headerStyle = css`
  background-color: #eeeeee;
  justify-content: flex-start;
  padding-top: 15px;
  border-bottom: 2px solid #e2e2e2;
`;

const nameStyle = css`
  font-size: 27px;
  padding-left: 25px;
  margin-bottom: 1rem;
  font-family: 'Noto Sans KR'
`;

export function ChatRoomContentHeader() {

  const {curChatRoom} = useCurChatRoom();

  return (
    <div css={headerStyle}>
      {curChatRoom !== null ? (
        <label css={nameStyle}>{curChatRoom.title}</label>
      ) : (
        <label css={nameStyle}>채팅방 선택</label>
      )}
    </div>
  )
}