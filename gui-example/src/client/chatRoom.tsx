import {gql, useMutation, useQuery} from "@apollo/client";
import {Mutation, Query} from "@/graphql/types.ts";
import {accountFields} from "@/client/account.tsx";

const chatRooms = gql`
  query ChatRooms {
    chatRooms {
      id
      createAccount {
        id
      }
      title
      password
      createDate
      type
      chatMessages {
        id
      }
      chatUsers {
        id
      }
    }
  }
`;

export function useChatRooms() {
  return useQuery<Query>(chatRooms);
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
    refetchQueries: [ "ChatRooms" ],
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
    refetchQueries: [ "ChatRooms" ],
  });
  return {deleteChatRoom};
}
