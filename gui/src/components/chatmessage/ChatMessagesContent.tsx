import React, {useEffect, useState} from "react";
import {ChatMessageList} from "@/components/chatmessage/ChatMessageList.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {css} from "@emotion/react";
import {useChatMessages} from "@/hooks/chatmessage/useChatMessages.ts";
import {Account, ChatUser} from "@/graphql/types.ts";
import {useChatMessagesRefreshStore} from "@/hooks/chatmessage/useChatMessagesRefreshStore.ts";

const inputFrameStyle = css`
  height: 20%;
  display: flex;
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 40px;
  justify-content: space-between;
  width: 100%;
`;

const messageInputStyle = css`
  border: 2px solid #e2e2e2 !important;
  font-family: 'Noto Sans KR';
  font-size: 25px !important;
  flex-grow: 1 !important;
  height: 70px !important;
`;

const sendButtonStyle = css`
  width: 30% !important;
  height: 70px !important;
  background-color: #222831 !important;
`;

interface ChatMessagesContentProps {
  chatRoomId: number;
  myInfo: Account;
  chatUsers: ChatUser[];
}

export function ChatMessagesContent({ chatRoomId, myInfo, chatUsers }: ChatMessagesContentProps) {

  const {refreshFlag} = useChatMessagesRefreshStore();
  const [chatMessageInput, setChatMessageInput] = useState("");

  const {
    chatMessages,
    // loading,
    connect, disconnect, send,
    observerRef, scrollRef,
  } = useChatMessages(chatRoomId, myInfo, chatUsers);

  useEffect(() => {
    disconnect();
    connect();
  }, [refreshFlag]);

  async function onSend() {
    if (!chatRoomId) {
      throw Error("chatRoomId is null");
    }
    await send(chatMessageInput);
    setChatMessageInput("");
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onSend();
    }
  };

  return (
    <>
      <ChatMessageList
        chatMessages={chatMessages}
        scrollRef={scrollRef}
        observerRef={observerRef}
      />
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
      {/*{loading && (<div>loading...</div>)}*/}
    </>
  )
}