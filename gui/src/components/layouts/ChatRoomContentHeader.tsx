import {css} from "@emotion/react";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";
import {HStack} from "@/lib/style/layouts.tsx";
import {rightAlignStyle} from "@/styles/globalStyles.ts";

const headerStyle = css`
  border-bottom: 2px solid #e2e2e2;
`;

const nameStyle = css`
  font-size: 1.4rem;
  font-weight: 600;
  margin-left: 1rem;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  font-family: 'Noto Sans KR'
`;

export function ChatRoomContentHeader() {

  const {curChatRoom} = useCurChatRoom();

  return (
    <div css={headerStyle}>
      <HStack>
        {curChatRoom !== null ? (
          <label css={nameStyle}>{curChatRoom.title}</label>
        ) : (
          <label css={nameStyle}>채팅방 선택</label>
        )}
        <div css={rightAlignStyle}>
          hello
        </div>
      </HStack>
    </div>
)
}