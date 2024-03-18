import {gql} from "@apollo/client";
import {consts} from "@/configures/consts.ts";
import {useQuery} from "@/client/graphql_utils.ts";

export const accountColumns = gql`
    fragment accountColumns on Account {
        id
        role
        username
        nickname
    }
`;

const meQL = gql`
    query Me {
        account {
            ...accountColumns
        }
    }
    ${accountColumns}
`;

export function useMe() {
  return useQuery(meQL);
}

export const accountsAllQL = gql`
    query AccountsAll {
        accountsAll {
            ...accountColumns
        }
    }
    ${accountColumns}
`;

export function useAccounts() {
  return useQuery(accountsAllQL);
}

export function login(username: string, password: string) {
  return fetch(
    `${consts.endpoint}/api/auth/login`,
    {
      method: 'POST',
      body: JSON.stringify({username, password}),
      credentials: "include",
    }
  );
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
