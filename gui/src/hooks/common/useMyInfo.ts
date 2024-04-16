import {useNavigate} from "react-router";
import {useEffect} from "react";
import {useQuery} from "@apollo/client";
import {myInfoQL} from "@/client/account.ts";
import {Query} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";

export function useMyInfo() {

  const {data: myInfoData, error} = useQuery<Query>(myInfoQL);
  const myInfo = myInfoData?.account ?? undefined;

  const navigate = useNavigate();

  useEffect(() => {
    if (error) {
      if (consts.isDev) {
        navigate("/account-select");
      } else {
        navigate("/login");
      }
    }
  }, [error]);

  return {myInfo, error};
}
