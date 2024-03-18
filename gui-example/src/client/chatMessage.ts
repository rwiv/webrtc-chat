import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {chatRoomColumns} from "@/client/chatRoom.ts";
import {accountColumns} from "@/client/account.ts";
import {useQuery} from "@/client/graphql_utils.ts";
import {chatUserColumns} from "@/client/chatUser.ts";

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
    }
`;

export const chatRoomAndMessagesQL = gql`
    query ChatRoomAndMessages($id: Long) {
        chatRoom(id: $id) {
            ...chatRoomColumns
            chatMessages {
                ...chatMessageColumns
                createAccount {
                    ...accountColumns
                }
            }
            chatUsers {
                ...chatUserColumns
                account {
                    ...accountColumns
                }
            }
        }
    }
    ${accountColumns}
    ${chatRoomColumns}
    ${chatMessageColumns}
    ${chatUserColumns}
`;

export function useChatRoomAndMessages(chatRoomId: number) {
    return useQuery(chatRoomAndMessagesQL, { id: chatRoomId });
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
