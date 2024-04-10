import {Button} from "@/components/ui/button.tsx";
import {useState} from "react";
import {Account, ChatUser} from "@/graphql/types.ts";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {Input} from "@/components/ui/input.tsx";
import {useChatRoomAndUsers} from "@/client/chatUser.ts";
import {useChatMessages} from "@/hooks/chatmessage/useChatMessages.ts";

const chatRoomId = 20;

export function TestPage() {

  const {myInfo} = useMyInfo();
  const {data: users} = useChatRoomAndUsers(chatRoomId);
  const chatUsers = users?.chatRoom?.chatUsers ?? undefined;

  return (
    <div>
      {myInfo !== undefined && chatUsers !== undefined && (
        <Comp chatRoomId={chatRoomId} myInfo={myInfo} chatUsers={chatUsers} />
      )}
    </div>
  )
}

interface CompProps {
  chatRoomId: number;
  myInfo: Account;
  chatUsers: ChatUser[];
}

export function Comp({ chatRoomId, myInfo, chatUsers }: CompProps) {

  const [msgInput, setMsgInput] = useState("");

  const {chatMessages, loading, send} = useChatMessages(chatRoomId, myInfo, chatUsers);

  const onSend = async () => {
    await send(msgInput);
    setMsgInput("");
  }

  return (
    <div>
      {chatMessages.map((chatMessage, idx) => (
        <div key={idx}>{chatMessage.content}</div>
      ))}
      <Input
        id="name"
        className="w-56 m-3"
        onChange={event => setMsgInput(event.target.value)}
        value={msgInput}
      />
      <div className="m-3">
        {/*<Button disabled={loading} onClick={onSend}>Send</Button>*/}
        <Button onClick={onSend}>Send</Button>
      </div>
      {loading && (
        <div>loading...</div>
      )}
    </div>
  )
}
