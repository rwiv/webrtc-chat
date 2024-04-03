import {HStack} from "@/lib/layouts.tsx";
import {ChatRoomCreateButton} from "@/components/chatroom/ChatRoomCreateButton.tsx";
import {ChatRoomSidebarList} from "@/components/chatroom/ChatRoomSidebarList.tsx";
import {MyInfo} from "@/components/account/MyInfo.tsx";
import {css} from "@emotion/react";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {useChatRooms} from "@/hooks/useChatRooms.ts";

const inputStyle = css`
    display: flex; /* 플렉스박스 사용 */
    justify-content: space-between; /* 양 끝에 아이템을 놓기 */
    align-items: center; /* 세로 중앙 정렬 */
    padding-top : 20px;
    padding-bottom: 20px;
    padding-left: 20px;
    //padding-right: 20px;
`;

const labelStyle = css`
    color: white;
    font-size: 20px;
`;

export function ChatRoomSidebar() {

  const {myInfo} = useMyInfo();
  const {
    chatRooms,
    ref,
    addChatRoom,
    // removeChatRoom,
  } = useChatRooms();

  return (
    <>
      <HStack css={inputStyle}>
        <label css={labelStyle}>Chat Room List</label>
        <ChatRoomCreateButton addChatRoom={addChatRoom}/>
      </HStack>
      <ChatRoomSidebarList myInfo={myInfo} chatRooms={chatRooms} observerRef={ref} />
      <MyInfo />
    </>
  )
}