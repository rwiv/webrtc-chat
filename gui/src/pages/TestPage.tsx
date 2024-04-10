import {Button} from "@/components/ui/button.tsx";
import {useEffect, useState} from "react";
import {Account, ChatUser} from "@/graphql/types.ts";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {Input} from "@/components/ui/input.tsx";
import {useChatRoomAndUsers} from "@/client/chatUser.ts";
import {useChatMessagesP2PBasedRTC} from "@/hooks/chatmessage/useChatMessagesP2PBasedRTC.ts";

const chatRoomId = 20;

export function TestPage() {

  const {myInfo} = useMyInfo();
  const {data: users} = useChatRoomAndUsers(chatRoomId);
  const chatUsers = users?.chatRoom?.chatUsers;

  return (
    <div>
      {myInfo !== undefined && myInfo !== null
        && chatUsers !== undefined && chatUsers !== null && (
        <Comp myInfo={myInfo} chatUsers={chatUsers} />
      )}
    </div>
  )
}

interface CompProps {
  myInfo: Account;
  chatUsers: ChatUser[];
}

export function Comp({ myInfo, chatUsers }: CompProps) {

  const [msgInput, setMsgInput] = useState("");

  const [messages, setMessages] = useState<string[]>([]);
  const {loading, connect, disconnect, broadcast} = useChatMessagesP2PBasedRTC(chatRoomId, myInfo, chatUsers, setMessages);

  useEffect(() => {
    connect();
    return () => {
      disconnect();
    }
  }, []);

  const onSend = () => {
    broadcast(msgInput);
    setMsgInput("");
  }

  return (
    <div>
      {messages.map((msg, idx) => (
        <div key={idx}>{msg}</div>
      ))}
      <Input
        id="name"
        className="w-56 m-3"
        onChange={event => setMsgInput(event.target.value)}
        value={msgInput}
      />
      <div className="m-3">
        <Button disabled={loading} onClick={onSend}>Send</Button>
        {/*<Button onClick={onSend}>Send</Button>*/}
      </div>
      {loading && (
        <div>loading...</div>
      )}
    </div>
  )
}
