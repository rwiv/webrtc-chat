import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {useQuery} from "@/lib/web/apollo.ts";
import {AccountCreation} from "@/graphql/types.ts";
import {post} from "@/lib/web/http.ts";

export const accountColumns = gql`
    fragment accountColumns on Account {
        id
        role
        username
        nickname
        avatarUrl
    }
`;

export const myInfoQL = gql`
    query MyInfo {
        account {
            ...accountColumns
        }
    }
    ${accountColumns}
`;

export const myFriendsQL = gql`
    query myFriends {
        account {
            ...accountColumns
            friends {
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

export function signup(creation: AccountCreation) {
  return post(`${consts.endpoint}/api/auth/signup`, creation);
}

export interface LoginRequest {
  username: string;
  password: string;
}

export function login(req: LoginRequest, remember: boolean) {
  let url = `${consts.endpoint}/api/auth/login`;
  if (remember) {
    url += "?remember=true";
  }
  return post(url, req);
}

export function logout() {
  return post(`${consts.endpoint}/api/auth/logout`, null);
}
