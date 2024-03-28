import {useDeleteChatRoom} from "@/client/chatRoom.ts";
import {ChatRoom, Query} from "@/graphql/types.ts";
import {useApolloClient} from "@apollo/client";
import {Button} from "@/components/ui/button.tsx";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useNavigate} from "react-router";
import {useMyInfo} from "@/client/account.ts";
import {useChatRooms} from "@/hooks/useChatRooms.ts";

export function ChatRoomList() {
  const client = useApolloClient();
  const navigate = useNavigate();
  const {chatRooms, ref} = useChatRooms();

  const {data: me} = useMyInfo();
  const {deleteChatRoom} = useDeleteChatRoom();
  const {createChatUser} = useCreateChatUser();

  const onDeleteChatRoom = async (chatRoomId: number) => {
    const variables = {
      chatRoomId,
    }
    const res = await deleteChatRoom({ variables})
    console.log(res.data?.deleteChatRoom);
  }

  const onClickLink = async (chatRoom: ChatRoom) => {
    const data = await client.query<Query>({
      query: chatRoomAndUsersQL,
      variables: { id: chatRoom.id },
      // network-only로 설정하지 않으면 이전 chatRoom을 exit해도
      // 이전 cache가 적용되어 exit하지 않은 것으로 처리되어 에러 발생
      fetchPolicy: "network-only",
    });
    const filtered = data.data?.chatRoom?.chatUsers?.filter(it => {
      return it.account.id === me?.account?.id;
    });
    if (filtered?.length === 0) {
      const variables = {
        chatRoomId: chatRoom.id,
        password: null,
      }
      const res = await createChatUser({variables})
      console.log(res.data);
    }
    navigate(`/chat-rooms/${chatRoom.id}`);
  }

  return (
    <div className="h-48 w-48 overflow-y-auto" ref={ref}>
      {chatRooms?.map(chatRoom => (
        <div key={chatRoom.id}>
          <Button
            variant="link"
            onClick={() => onClickLink(chatRoom)}
          >
            {chatRoom.title}
          </Button>
          <Button variant="ghost" onClick={() => onDeleteChatRoom(chatRoom.id)}>x</Button>
        </div>
      ))}
      {chatRooms.length > 0 && (
        <div ref={ref} className="h-[1rem]"/>
      )}
    </div>
  )
}
