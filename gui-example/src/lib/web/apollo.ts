import {useQuery as useQueryOrigin} from "@apollo/client";
import type {DocumentNode, OperationVariables, TypedDocumentNode} from "@apollo/client/core";
import type {NoInfer, QueryHookOptions} from "@apollo/client/react/types/types";
import {Query} from "@/graphql/types.ts";

type Options = QueryHookOptions<NoInfer<Query>, NoInfer<OperationVariables>>;

export function useQuery(
  query: DocumentNode | TypedDocumentNode<Query, OperationVariables>,
  variables?: any,
  options?: Options,
) {
  let newOpts: Options = {
    ...options,
    fetchPolicy: "cache-and-network",
  };
  if (variables !== undefined && variables !== null) {
    newOpts = {
      ...newOpts,
      variables: { ...variables },
    }
  }
  return useQueryOrigin(query, newOpts);
}

export function getQueryName(node: DocumentNode): string {
  const queries = node.definitions.filter((it: any) => it?.operation === "query");
  if (queries.length !== 1) {
    throw Error("not found query");
  }
  const query: any = queries[0];
  const result = query?.name?.value;
  if (typeof result !== "string") {
    throw Error("not found query name");
  }
  return result;
}
