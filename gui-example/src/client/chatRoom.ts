import {gql, useMutation, useQuery} from "@apollo/client";
import {Mutation, Query} from "@/graphql/types.ts";
import {accountDefaultFields} from "@/client/account.ts";

export const chatRoomDefaultFields = `
    fragment chatRoomDefaultFields on ChatRoom {
        id
        password
        title
        createDate
        type
        createAccount {
            id
        }
    }
`;

const chatRoomsAll = gql`
    query ChatRoomsAll {
        chatRoomsAll {
            ...chatRoomDefaultFields
        }
    }
    
    fragment chatRoomDefaultFields on ChatRoom {
        id
        password
        title
        createDate
        type
        createAccount {
            id
        }
    }
`;

export function useChatRoomsAll() {
  return useQuery<Query>(chatRoomsAll);
}

const createChatRoomQL = gql`
    mutation CreateChatRoom($req: ChatRoomCreateRequest!) {
        createChatRoom(req: $req) {
            ...chatRoomDefaultFields
            createAccount {
                ...accountDefaultFields
            }
        }
    }
    ${chatRoomDefaultFields}
    ${accountDefaultFields}
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
            ...chatRoomDefaultFields
            createAccount {
                ...accountDefaultFields
            }
        }
    }
    ${chatRoomDefaultFields}
    ${accountDefaultFields}
`;

export function useDeleteChatRoom() {
  const [deleteChatRoom] = useMutation<Mutation>(deleteChatRoomQL, {
    refetchQueries: [ "ChatRoomsAll" ],
  });
  return {deleteChatRoom};
}
