import React, {useState} from "react";
import {sendMessage} from "@/client/chatMessage.ts";
import {ChatMessageList} from "@/components/chatmessage/ChatMessageList.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {css} from "@emotion/react";

const mainStyle = css`
    background-color: #eeeeee;
    //margin-top: 1rem;
    //flex-grow: 1;
`;

const inputFrameStyle = css`
    background-color: #eeeeee;
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
}

export function ChatMessagesContent({ chatRoomId }: ChatMessagesContentProps) {

  const [chatMessageInput, setChatMessageInput] = useState("");

  async function send() {
    if (!chatRoomId) {
      throw Error("chatRoomId is null");
    }
    await sendMessage(chatRoomId, chatMessageInput);
    setChatMessageInput("");
  }

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    setState: React.Dispatch<React.SetStateAction<string>>,
  ) => {
    setState(e.target.value);
  };

  return (
    <>
      <div css={mainStyle}>
        <ChatMessageList chatRoomId={chatRoomId}/>
      </div>
      <div css={inputFrameStyle}>
        <div className="flex w-full items-center space-x-2">
          <Input
            type="message" id="inputField" css={messageInputStyle}
            onChange={(e: any) => handleChange(e, setChatMessageInput)}
            value={chatMessageInput}
          />
          <Button
            type="submit" id="inputButton" css={sendButtonStyle}
            onClick={() => send()}
          >
            send
          </Button>
        </div>
      </div>
    </>
  )
}