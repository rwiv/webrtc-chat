import {gql, useMutation} from "@apollo/client";
import {Mutation} from "@/graphql/types.ts";
import {accountColumns} from "@/client/account.ts";

export const defaultChatRoomSize: number = 5;

export const chatRoomColumns = gql`
    fragment chatRoomColumns on ChatRoom {
        id
        createdBy {
            id
        }
        title
        createdAt
        type
        chatUserCnt
        hasPassword
    }
`;

export const chatRoomsQL = gql`
    query ChatRooms($page: Int!, $size: Int!) {
        chatRooms(page: $page, size: $size) {
            ...chatRoomColumns
            createdBy {
                ...accountColumns
            }
        }
    }
    ${chatRoomColumns}
    ${accountColumns}
`;

const createChatRoomQL = gql`
    mutation CreateChatRoom($req: ChatRoomCreateRequest!) {
        createChatRoom(req: $req) {
            ...chatRoomColumns
            createdBy {
                ...accountColumns
            }
        }
    }
    ${accountColumns}
    ${chatRoomColumns}
`;

export function useCreateChatRoom() {
  const [createChatRoom, {loading, error}] = useMutation<Mutation>(createChatRoomQL);
  return {createChatRoom, loading, error};
}

const deleteChatRoomQL = gql`
    mutation DeleteChatRoom($chatRoomId: Long!) {
        deleteChatRoom(chatRoomId: $chatRoomId) {
            ...chatRoomColumns
            createdBy {
                ...accountColumns
            }
        }
    }
    ${accountColumns}
    ${chatRoomColumns}
`;

export function useDeleteChatRoom() {
  const [deleteChatRoom, {loading, error}] = useMutation<Mutation>(deleteChatRoomQL);
  return {deleteChatRoom, loading, error};
}
