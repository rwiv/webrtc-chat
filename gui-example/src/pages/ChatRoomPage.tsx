import {useParams} from "react-router";

export function ChatRoomPage() {

  const params = useParams();
  const chatRoomId = params["chatRoomId"];

  return (
    <>
      <div>{chatRoomId}</div>
    </>
  )
}