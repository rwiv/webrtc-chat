import {useQuery, gql} from "@apollo/client";
import {Query} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";

export const accountFields = `
  fragment accountFields on Account {
    id
    role
    username
    nickname
  }
`;

const meQL = gql`
    query Me {
        me {
            ...accountFields
        }
    }

    fragment accountFields on Account {
        id
        role
        username
        nickname
    }
`;

export function useMe() {
  return useQuery<Query>(meQL);
}

const accounts = gql`
    query Accounts {
        accounts {
            ...accountFields
        }
    }
    ${accountFields}
`;

export function useAccounts() {
  return useQuery<Query>(accounts);
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
