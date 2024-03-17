import {gql, useMutation, useQuery} from "@apollo/client";
import {Mutation, Query} from "@/graphql/types.ts";
import {chatRoomDefaultFields} from "@/client/chatRoom.ts";
import {accountDefaultFields} from "@/client/account.ts";

export const chatRoomAndUsersQL = gql`
    query ChatRoomAndUsers($id: Long!) {
        chatRoom(id: $id) {
            ...chatRoomDefaultFields
            chatUsers {
                id
                account {
                    ...accountDefaultFields
                }
            }
        }
    }
    ${chatRoomDefaultFields}
    ${accountDefaultFields}
`;

export function useChatRoomAndUsers(chatRoomId: number) {
  const variables = { id: chatRoomId };
  return useQuery<Query>(chatRoomAndUsersQL, {variables});
}

const createChatUserQL = gql`
    mutation CreateChatUser($chatRoomId: Long!, $password: String) {
        createChatUser(chatRoomId: $chatRoomId, password: $password) {
            id
            chatRoom {
                id
            }
            account {
                id
            }
        }
    }
`;

export function useCreateChatUser() {
  const [createChatUser] = useMutation<Mutation>(createChatUserQL, {
    refetchQueries: [ "ChatRoomAndUsers" ],
  });
  return {createChatUser};
}

const deleteChatUserMeQL = gql`
    mutation DeleteChatUserMe($chatRoomId: Long!) {
        deleteChatUserMe(chatRoomId: $chatRoomId) {
            id
            chatRoom {
                id
            }
            account {
                id
            }
        }
    }
`;

export function useDeleteChatUserMe() {
    const [deleteChatUserMe] = useMutation<Mutation>(deleteChatUserMeQL, {
        refetchQueries: [ "ChatRoomAndUsers" ],
    });
    return {deleteChatUserMe};
}
