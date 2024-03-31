import {gql, useMutation} from "@apollo/client";
import {Mutation} from "@/graphql/types.ts";
import {chatRoomColumns} from "@/client/chatRoom.ts";
import {accountColumns} from "@/client/account.ts";
import {getQueryName, useQuery} from "@/client/graphql_utils.ts";

export const chatUserColumns = gql`
    fragment chatUserColumns on ChatUser {
        id
        chatRoom {
            id
        }
        account {
            id
        }
    }
`;

export const chatRoomAndUsersQL = gql`
    query ChatRoomAndUsers($id: Long!) {
        chatRoom(id: $id) {
            ...chatRoomColumns
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
    ${chatUserColumns}
`;

export function useChatRoomAndUsers(chatRoomId: number) {
  return useQuery(chatRoomAndUsersQL, { id: chatRoomId });
}

const createChatUserQL = gql`
    mutation CreateChatUser($chatRoomId: Long!, $password: String) {
        createChatUser(chatRoomId: $chatRoomId, password: $password) {
            ...chatUserColumns
        }
    }
    ${chatUserColumns}
`;

export function useCreateChatUser() {
  const [createChatUser, {loading, error}] = useMutation<Mutation>(createChatUserQL, {
    refetchQueries: [ getQueryName(chatRoomAndUsersQL) ],
  });
  return {createChatUser, loading, error};
}

const deleteChatUserMeQL = gql`
    mutation DeleteChatUserMe($chatRoomId: Long!) {
        deleteChatUserMe(chatRoomId: $chatRoomId) {
            ...chatUserColumns
        }
    }
    ${chatUserColumns}
`;

export function useDeleteChatUserMe() {
    const [deleteChatUserMe, {loading, error}] = useMutation<Mutation>(deleteChatUserMeQL, {
        refetchQueries: [ getQueryName(chatRoomAndUsersQL) ],
    });
    return {deleteChatUserMe, loading, error};
}
