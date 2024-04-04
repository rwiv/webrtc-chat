import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {useQuery} from "@/client/graphql_utils.ts";
import {AccountCreation} from "@/graphql/types.ts";

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
  return fetch(
    `${consts.endpoint}/api/auth/signup`,
    {
      method: 'POST',
      body: JSON.stringify(creation),
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
    }
  );
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
  return fetch(url, {
    method: 'POST',
    body: JSON.stringify(req),
    credentials: "include",
  });
}

export function logout() {
  return fetch(
    `${consts.endpoint}/api/auth/logout`,
    {
      method: 'POST',
      credentials: "include",
    }
  );
}
