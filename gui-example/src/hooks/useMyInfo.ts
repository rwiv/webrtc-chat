import {useNavigate} from "react-router";
import {useEffect} from "react";
import {useQuery} from "@apollo/client";
import {getMyInfoQL} from "@/client/account.ts";

export function useMyInfo() {

  const {data: myInfoData, error} = useQuery(getMyInfoQL);
  const myInfo = myInfoData?.account;

  const navigate = useNavigate();

  useEffect(() => {
    if (error) navigate("/account-select");
  }, [error]);

  return {myInfo, error};
}
