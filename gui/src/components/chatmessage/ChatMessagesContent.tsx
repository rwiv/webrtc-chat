import React, {useState} from "react";
import {ChatMessageList} from "@/components/chatmessage/ChatMessageList.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {css} from "@emotion/react";
import {useChatMessages} from "@/hooks/chatmessage/useChatMessages.ts";
import {Account, ChatUser} from "@/graphql/types.ts";

const inputFrameStyle = css`
  height: 20%;
  display: flex;
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 40px;
  justify-content: space-between;
  width: 100%;
`;

interface ChatMessagesContentProps {
  chatRoomId: number;
  myInfo: Account;
  chatUsers: ChatUser[];
}

export function ChatMessagesContent({ chatRoomId, myInfo, chatUsers }: ChatMessagesContentProps) {

  const {
    chatMessages, send,
    observerRef, scrollRef,
  } = useChatMessages(chatRoomId, myInfo, chatUsers);


  return (
    <>
      <ChatMessageList
        chatMessages={chatMessages}
        scrollRef={scrollRef}
        observerRef={observerRef}
      />
      <ChatMessageInput send={send} />
    </>
  )
}

interface ChatMessageInputProps {
  send: (message: string) => Promise<void>;
}

const messageInputStyle = css`
  border: 2px solid #e2e2e2;
  font-size: 1.1rem;
  height: 3rem;
`;

const sendButtonStyle = css`
  width: 30%;
  height: 3rem;
  background-color: #222831;
`;

function ChatMessageInput({ send }: ChatMessageInputProps) {

  const [chatMessageInput, setChatMessageInput] = useState("");

  async function onSend() {
    await send(chatMessageInput);
    setChatMessageInput("");
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onSend();
    }
  };

  return (
    <div css={inputFrameStyle}>
      <div className="flex w-full items-center space-x-2">
        <Input
          type="message" id="inputField" css={messageInputStyle}
          onChange={e => setChatMessageInput(e.target.value)}
          value={chatMessageInput}
          onKeyDown={handleKeyDown}
        />
        <Button
          type="submit" id="inputButton" css={sendButtonStyle}
          onClick={() => onSend()}
        >
          send
        </Button>
      </div>
    </div>
  )
}
