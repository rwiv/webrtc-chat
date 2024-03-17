import {gql, useQuery} from "@apollo/client";
import {Query} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";
import {chatRoomDefaultFields} from "@/client/chatRoom.ts";
import {accountDefaultFields} from "@/client/account.ts";

const chatRoomAndMessages = gql`
    query ChatRoomAndMessages($id: Long) {
        chatRoom(id: $id) {
            ...chatRoomDefaultFields
            chatMessages {
                id
                createAccount {
                    id
                    nickname
                }
                content
                createAt
            }
            chatUsers {
                id
                chatRoom {
                    id
                }
                account {
                    ...accountDefaultFields
                }
            }
        }
    }
    ${chatRoomDefaultFields}
    ${accountDefaultFields}
`;

export function useChatRoomAndMessages(chatRoomId: number) {
    const variables = { id: chatRoomId };
    return useQuery<Query>(chatRoomAndMessages, {variables});
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
