import {login} from "@/client/account.tsx";
import {useNavigate} from "react-router";
import {useApolloClient} from "@apollo/client";

export function AccountSelectPage() {

  const client = useApolloClient();
  const navigate = useNavigate();

  const onLoginUser1 = async () => {
    await login("user1@gmail.com", "1234");
    await client.refetchQueries({ include: ["Me"] });
    navigate("/");
  }

  const onLoginUser2 = async () => {
    await login("user2@gmail.com", "1234");
    await client.refetchQueries({ include: ["Me"] });
    navigate("/");
  }

  return (
    <>
      <button onClick={onLoginUser1}>user1</button>
      <button onClick={onLoginUser2}>user2</button>
    </>
  )
}
