import {useChatRooms} from "@/hooks/useChatRooms.ts";
import {ChatRoomItem} from "@/components/chatroom/ChatRoomItem.tsx";
import {css} from "@emotion/react";

export function ChatRoomList() {

  const {chatRooms, ref} = useChatRooms();

  return (
    <div className="h-48 w-48 overflow-y-auto">
      {chatRooms?.map(chatRoom => (
        <ChatRoomItem key={chatRoom.id} chatRoom={chatRoom}/>
      ))}
      {chatRooms.length > 0 && (
        <div ref={ref} css={css({height: 0})} />
      )}
    </div>
  )
}
