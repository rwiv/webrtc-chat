import {Button} from "@/components/ui/button.tsx";
import {css} from "@emotion/react";
import {useNavigate} from "react-router";
import {useDeleteChatUserMe} from "@/client/chatUser.ts";
import {useDeleteChatRoom} from "@/client/chatRoom.ts";
import {useCurChatRoom} from "@/hooks/useCurChatRoom.ts";

const headerStyle = css`
    background-color: #eeeeee;
    //height: 10%;
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

const buttonSetStyle = css`
    float: right;
    margin-right: 1rem;
`;

interface ChatRoomHeaderProps {
  chatRoomId: number | null;
}

export function ChatRoomContentHeader({ chatRoomId }: ChatRoomHeaderProps) {

  const navigate = useNavigate();

  const {curChatRoom} = useCurChatRoom();
  const {deleteChatUserMe} = useDeleteChatUserMe();
  const {deleteChatRoom} = useDeleteChatRoom();

  const onExit = async () => {
    const variables = { chatRoomId };
    const res = await deleteChatUserMe({ variables })
    console.log(res);
    navigate("/");
  }

  const onDeleteChatRoom = async () => {
    const variables = {
      chatRoomId,
    }
    const res = await deleteChatRoom({ variables})
    console.log(res.data?.deleteChatRoom);
  }

  return (
    <div css={headerStyle}>
      {curChatRoom !== null ? (
        <label css={nameStyle}>{curChatRoom.title}</label>
      ) : (
        <label css={nameStyle}>채팅방 선택</label>
      )}
      {chatRoomId !== null && (
        <span css={buttonSetStyle}>
          <Button onClick={onDeleteChatRoom} className="mr-1">delete</Button>
          <Button onClick={onExit}>exit</Button>
        </span>
      )}
    </div>
  )
}