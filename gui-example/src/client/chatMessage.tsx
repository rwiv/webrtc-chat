import {gql, useQuery} from "@apollo/client";
import {Query} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";

const chatMessages = gql`
    query ChatMessages {
        chatMessages {
            id
            chatRoom {
                id
            }
            createAccount {
                id
                nickname
            }
            content
            createAt
        }
    }
`;

export function useChatMessages() {
  return useQuery<Query>(chatMessages);
}

const chatMessagesByChatRoomId = gql`
    query ChatMessagesByChatRoomId($chatRoomId: Long) {
        chatMessagesByChatRoomId(chatRoomId: $chatRoomId) {
            id
            chatRoom {
                id
            }
            createAccount {
                id
                nickname
            }
            content
            createAt
        }
    }
`;

export function useChatMessagesChatRoomId(chatRoomId: number) {
  const variables = { chatRoomId };
  return useQuery<Query>(chatMessagesByChatRoomId, {variables});
}

export async function sendMessage(chatRoomId: string, content: string) {
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
