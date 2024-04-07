import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {accountColumns} from "@/client/account.ts";
import {post} from "@/lib/web/http.ts";

export const defaultChatMessageSize: number = 10;

export const chatMessageColumns = gql`
    fragment chatMessageColumns on ChatMessage {
        id
        chatRoom {
            id
        }
        createdBy {
            id
        }
        content
        createdAt
        num
    }
`;

export const chatMessagesQL = gql`
    query ChatMessages($chatRoomId: Long!, $page: Int!, $size: Int!, $offset: Int!) {
        chatMessages(chatRoomId: $chatRoomId, page: $page, size: $size, offset: $offset) {
            ...chatMessageColumns
            createdBy {
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
            createdBy {
                ...accountColumns
            }
        }
    }
    ${chatMessageColumns}
    ${accountColumns}
`;

export async function sendMessage(chatRoomId: number, content: string) {
  return await post(`${consts.endpoint}/api/chat-messages/${chatRoomId}`, { content });
}
