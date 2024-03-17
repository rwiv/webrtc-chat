import {gql, useMutation, useQuery} from "@apollo/client";
import {Mutation, Query} from "@/graphql/types.ts";
import {accountFields} from "@/client/account.tsx";

const chatRoomsAll = gql`
    query ChatRoomsAll {
        chatRoomsAll {
            id
            createAccount {
                id
            }
            title
            password
            createDate
            type
        }
    }
`;

export function useChatRoomsAll() {
  return useQuery<Query>(chatRoomsAll);
}

const createChatRoomQL = gql`
    mutation CreateChatRoom($req: ChatRoomCreateRequest!) {
        createChatRoom(req: $req) {
            id
            title
            createDate
            createAccount {
                ...accountFields
            }
        }
    }
    ${accountFields}
`;

export function useCreateChatRoom() {
  const [createChatRoom] = useMutation<Mutation>(createChatRoomQL, {
    refetchQueries: [ "ChatRoomsAll" ],
  });
  return {createChatRoom};
}

const deleteChatRoomQL = gql`
    mutation DeleteChatRoom($chatRoomId: Long!) {
        deleteChatRoom(chatRoomId: $chatRoomId) {
            id
            title
            createDate
            createAccount {
                ...accountFields
            }
        }
    }
    ${accountFields}
`;

export function useDeleteChatRoom() {
  const [deleteChatRoom] = useMutation<Mutation>(deleteChatRoomQL, {
    refetchQueries: [ "ChatRoomsAll" ],
  });
  return {deleteChatRoom};
}
