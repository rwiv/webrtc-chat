import {gql, useMutation} from "@apollo/client";
import {Mutation} from "@/graphql/types.ts";
import {accountColumns} from "@/client/account.ts";
import {getQueryName, useQuery} from "@/client/graphql_utils.ts";

export const defaultChatRoomSize: number = 10;

export const chatRoomColumns = gql`
    fragment chatRoomColumns on ChatRoom {
        id
        createAccount {
            id
        }
        password
        title
        createDate
        type
    }
`;

const chatRoomsAllQL = gql`
    query ChatRoomsAll {
        chatRoomsAll {
            ...chatRoomColumns
        }
    }
    ${chatRoomColumns}
`;

export const chatRoomsQL = gql`
    query ChatRooms($page: Int!, $size: Int!) {
        chatRooms(page: $page, size: $size) {
            ...chatRoomColumns
        }
    }
    ${chatRoomColumns}
`;

export function useChatRoomsAll() {
  return useQuery(chatRoomsAllQL);
}

const createChatRoomQL = gql`
    mutation CreateChatRoom($req: ChatRoomCreateRequest!) {
        createChatRoom(req: $req) {
            ...chatRoomColumns
            createAccount {
                ...accountColumns
            }
        }
    }
    ${accountColumns}
    ${chatRoomColumns}
`;

export function useCreateChatRoom() {
  const [createChatRoom, {loading, error}] = useMutation<Mutation>(createChatRoomQL, {
    refetchQueries: [ getQueryName(chatRoomsAllQL) ],
  });
  return {createChatRoom, loading, error};
}

const deleteChatRoomQL = gql`
    mutation DeleteChatRoom($chatRoomId: Long!) {
        deleteChatRoom(chatRoomId: $chatRoomId) {
            ...chatRoomColumns
            createAccount {
                ...accountColumns
            }
        }
    }
    ${accountColumns}
    ${chatRoomColumns}
`;

export function useDeleteChatRoom() {
  const [deleteChatRoom, {loading, error}] = useMutation<Mutation>(deleteChatRoomQL, {
    refetchQueries: [ getQueryName(chatRoomsAllQL) ],
  });
  return {deleteChatRoom, loading, error};
}
