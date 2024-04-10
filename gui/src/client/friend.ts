import {gql, useMutation} from "@apollo/client";
import {useQuery} from "@/lib/web/apollo.ts";
import {accountColumns} from "@/client/account.ts";
import {chatRoomColumns} from "@/client/chatRoom.ts";
import {Mutation} from "@/graphql/types.ts";

export const myFriendsQL = gql`
    query myFriends {
        account {
            ...accountColumns
            friends {
                id
                to {
                    ...accountColumns
                }
            }
        }
    }
    ${accountColumns}
`;

export function useMyFriends() {
  return useQuery(myFriendsQL);
}

const createChatRoomByFriendQL = gql`
  mutation CreateChatRoomByFriend($friendId: Long!) {
      createChatRoomByFriend(friendId: $friendId) {
          ...chatRoomColumns
      }
  }
  ${chatRoomColumns}
`;

export function useCreateChatRoomByFriend() {
  const [createChatRoomByFriend, {loading, error}] = useMutation<Mutation>(createChatRoomByFriendQL);
  return {createChatRoomByFriend, loading, error};
}
