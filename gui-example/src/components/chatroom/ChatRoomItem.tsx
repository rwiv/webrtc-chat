import {Button} from "@/components/ui/button.tsx";
import {ChatRoom, Query} from "@/graphql/types.ts";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useApolloClient} from "@apollo/client";
import {useDeleteChatRoom} from "@/client/chatRoom.ts";
import {useMyInfo} from "@/client/account.ts";
import {useNavigate} from "react-router";

interface ChatRoomItemProps {
  chatRoom: ChatRoom;
}

export function ChatRoomItem({ chatRoom }: ChatRoomItemProps) {

  const client = useApolloClient();
  const navigate = useNavigate();
  const {data: me} = useMyInfo();
  const {deleteChatRoom} = useDeleteChatRoom();
  const {createChatUser} = useCreateChatUser();

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

  const onDeleteChatRoom = async (chatRoomId: number) => {
    const variables = {
      chatRoomId,
    }
    const res = await deleteChatRoom({ variables})
    console.log(res.data?.deleteChatRoom);
  }

  return (
    <div>
      <Button
        variant="link"
        onClick={() => onClickLink(chatRoom)}
      >
        {chatRoom.title}
      </Button>
      <Button variant="ghost" onClick={() => onDeleteChatRoom(chatRoom.id)}>x</Button>
    </div>
  )
}
