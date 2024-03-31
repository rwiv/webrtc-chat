import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {accountColumns} from "@/client/account.ts";

export const defaultChatMessageSize: number = 10;

export const chatMessageColumns = gql`
    fragment chatMessageColumns on ChatMessage {
        id
        chatRoom {
            id
        }
        createAccount {
            id
        }
        content
        createAt
        num
    }
`;

export const chatMessagesQL = gql`
    query ChatMessages($chatRoomId: Long!, $page: Int!, $size: Int!, $offset: Int!) {
        chatMessages(chatRoomId: $chatRoomId, page: $page, size: $size, offset: $offset) {
            ...chatMessageColumns
            createAccount {
                ...accountColumns
            }
        }
    }
    ${chatMessageColumns}
    ${accountColumns}
`;

export const chatMessageQL = gql`
    query ChatMessage($id: Long!) {
        chatMessage(id: $id) {
            ...chatMessageColumns
            createAccount {
                ...accountColumns
            }
        }
    }
    ${chatMessageColumns}
    ${accountColumns}
`;

export async function sendMessage(chatRoomId: number, content: string) {
  return await fetch(
    `${consts.endpoint}/api/chat-messages/${chatRoomId}`,
    {
      method: "POST",
      body: JSON.stringify({ content }),
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
    }
  );
}
