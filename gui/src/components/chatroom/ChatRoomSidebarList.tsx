import {Account, ChatRoom, Query} from "@/graphql/types.ts";
import {chatRoomAndUsersQL, useCreateChatUser} from "@/client/chatUser.ts";
import {useApolloClient} from "@apollo/client";
import {css} from "@emotion/react";
import {useNavigate} from "react-router";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";
import {HStack} from "@/lib/style/layouts.tsx";
import {useSidebarState} from "@/hooks/global/useSidebarState.ts";
import React, {useRef, useState} from "react";
import {PasswordInputDialog} from "@/components/chatroom/PasswordInputDialog.tsx";
import {getPrettyDateString} from "@/lib/common/date.ts";
import { LockIcon } from "lucide-react";

const listStyle = css`
  overflow-y: auto;
  
  ::-webkit-scrollbar {
    width: 0.5rem;
  }
  ::-webkit-scrollbar-track {
    background: transparent;
  }
  ::-webkit-scrollbar-thumb {
    background: #222831;
    border-radius: 10px;
  }
  ::-webkit-scrollbar-thumb:hover {
    background: #555555;
  }
`;

const itemFrameStyle = css`
  color: #ffffff;
  padding: 0.7rem;
  margin: 0.3rem;
  cursor: pointer;
  border-color: #555555;
  border-width: thin;
  border-radius: 0.375rem;
`;

interface ChatRoomListProps {
  chatRooms: ChatRoom[];
  myInfo: Account | undefined | null;
  observerRef: React.RefObject<HTMLDivElement>;
}

export function ChatRoomSidebarList({ myInfo, chatRooms, observerRef }: ChatRoomListProps) {

  const client = useApolloClient();
  const navigate = useNavigate();

  const {createChatUser} = useCreateChatUser();
  const {setCurChatRoom} = useCurChatRoom();
  const {setSidebarState} = useSidebarState();

  const openRef = useRef<HTMLButtonElement>(null);
  const closeRef = useRef<HTMLButtonElement>(null);
  const [passwordChatRoom, setPasswordChatRoom] = useState<ChatRoom | undefined>(undefined);

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
      if (chatRoom.hasPassword) {
        setPasswordChatRoom(chatRoom);
        openRef.current?.click();
        return;
      }
      const variables = { chatRoomId: chatRoom.id };
      const res = await createChatUser({variables});
      console.log(res.data);
    }
    setCurChatRoom(chatRoom);
    setSidebarState("CHATROOM");
    navigate(`/chat-rooms/${chatRoom.id}`);
  }

  return (
      <div css={listStyle}>
        <PasswordInputDialog
            openRef={openRef}
            closeRef={closeRef}
            chatRoom={passwordChatRoom}
        />
        {chatRooms.map(chatRoom => (
          <div
            key={chatRoom.id}
            css={itemFrameStyle}
            onClick={() => onClickLink(chatRoom)}
          >
            <HStack>
              <div css={{display: 'flex', alignItems: 'center', width: "50%"}}>
                <span css={{fontWeight: 600, fontSize: "1.1rem"}}>{chatRoom.title}</span>
                {chatRoom?.hasPassword && (<span><LockIcon style={{ marginLeft: '1rem', width: '1rem', height: '1rem' }} /></span>)}
              </div>
              <div>
                <div css={{color: "#aaaaaa", fontSize: "0.9rem"}}>
                  {getPrettyDateString(chatRoom.createdAt)}
                </div>
              </div>
            </HStack>
            <HStack>
              <div css={{width: "50%"}}>
                <div css={{color: "#eeeeee", fontSize: "0.9rem"}}>{chatRoom.createdBy.nickname}</div>
              </div>
              <div>
                <div css={{color: "#eeeeee", fontSize: "0.9rem"}}>{chatRoom.chatUserCnt}명 참여중</div>
              </div>
            </HStack>
          </div>
        ))}
        <div ref={observerRef} css={css({height: "1rem"})}/>
      </div>
  )
}
