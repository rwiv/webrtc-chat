import {Account, ChatRoom, Query} from "@/graphql/types.ts";
import {Button} from "@/components/ui/button.tsx";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useApolloClient} from "@apollo/client";
import {css} from "@emotion/react";
import {useNavigate} from "react-router";
import {useCurChatRoom} from "@/hooks/useCurChatRoom.ts";

const listStyle = css`
    //flex-grow: 1;
    overflow-y: auto;
`;

const itemStyle = css`
    display: flex;
    align-items: center;
    padding: 5px;
`;

const buttonStyle = css`
    width: 15rem;
    color: white;
    padding-left: 30px !important;
    justify-content: flex-start !important;
    align-items: center !important;
`;

interface ChatRoomListProps {
  chatRooms: ChatRoom[];
  myInfo: Account;
  observerRef: React.RefObject<HTMLDivElement>;
}

export function ChatRoomSidebarList({ myInfo, chatRooms, observerRef }: ChatRoomListProps) {

  const client = useApolloClient();
  const navigate = useNavigate();

  const {createChatUser} = useCreateChatUser();
  const {setCurChatRoom} = useCurChatRoom();

  const onClickLink = async (chatRoom: ChatRoom) => {
    const data = await client.query<Query>({
      query: chatRoomAndUsersQL,
      variables: { id: chatRoom.id },
      // network-only로 설정하지 않으면 이전 chatRoom을 exit해도
      // 이전 cache가 적용되어 exit하지 않은 것으로 처리되어 에러 발생
      fetchPolicy: "network-only",
    });
    const filtered = data.data?.chatRoom?.chatUsers?.filter(it => {
      return it.account.id === myInfo?.id;
    });
    if (filtered?.length === 0) {
      const variables = {
        chatRoomId: chatRoom.id,
        password: null,
      }
      const res = await createChatUser({variables})
      console.log(res.data);
    }
    setCurChatRoom(chatRoom);
    navigate(`/chat-rooms/${chatRoom.id}`);
  }

  return (
    <div css={listStyle}>
      {chatRooms.map(chatRoom => (
        <div key={chatRoom.id} css={itemStyle}>
          <Button variant="ghost" css={buttonStyle} onClick={() => onClickLink(chatRoom)}>
            {chatRoom.title}
          </Button>
        </div>
      ))}
      <div ref={observerRef} css={css({height: "1rem"})}/>
    </div>
  )
}
