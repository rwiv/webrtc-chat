import {myInfoQL, login, LoginRequest} from "@/client/account.ts";
import {useNavigate} from "react-router";
import {useApolloClient} from "@apollo/client";
import {Link} from "react-router-dom";
import {Button} from "@/components/ui/button.tsx";
import {getQueryName} from "@/lib/web/apollo.ts";

export function AccountSelectPage() {

  const client = useApolloClient();
  const navigate = useNavigate();

  const onLoginUser1 = async () => {
    const req: LoginRequest = {
      username: "user1@gmail.com",
      password: "1234",
    };
    await login(req, false);
    await client.refetchQueries({ include: [getQueryName(myInfoQL)] });
    navigate("/");
  }

  const onLoginUser2 = async () => {
    const req: LoginRequest = {
      username: "user2@gmail.com",
      password: "1234",
    };
    await login(req, false);
    await client.refetchQueries({ include: [getQueryName(myInfoQL)] });
    navigate("/");
  }

  const onLoginUser3 = async () => {
    const req: LoginRequest = {
      username: "user3@gmail.com",
      password: "1234",
    };
    await login(req, false);
    await client.refetchQueries({ include: [getQueryName(myInfoQL)] });
    navigate("/");
  }

  return (
    <>
      <div>
        <Button onClick={onLoginUser1}>user1</Button>
        <Button onClick={onLoginUser2}>user2</Button>
        <Button onClick={onLoginUser3}>user3</Button>
      </div>
      <div>
        <Link to={"/users/signup"}><Button>회원가입</Button></Link>
        <Link to={"/users/login"}><Button>로그인</Button></Link>
      </div>
    </>
  )
}
