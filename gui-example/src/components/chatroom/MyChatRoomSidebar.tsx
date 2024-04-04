import {useMyChatUsers} from "@/client/chatUser.ts";

export function MyChatRoomSidebar() {

  const {data} = useMyChatUsers();
  const chatUsers = data?.account?.chatUsers;

  const getSortedChatRooms = () => {
    if (chatUsers === undefined || chatUsers === null) {
      return [];
    }
    return [...chatUsers]
      .sort((a, b) => Date.parse(b.createdAt) - Date.parse(a.createdAt))
      .map(it => it.chatRoom)
  }

  return (
    <div>
      {getSortedChatRooms().map(chatRoom => (
        <div key={chatRoom.id}># {chatRoom.title}</div>
      ))}
    </div>
  )
}
