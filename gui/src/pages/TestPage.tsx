import {useEffect} from "react";
import {env} from "@/configures/env.ts";
import {consts} from "@/configures/consts.ts";

export function TestPage() {

  useEffect(() => {
    console.log(consts.isDev)
    console.log(consts.isProd)
    console.log(env);
  }, []);

  return (
    <div>
      <div css={{width: "20rem"}}>
        {/*<FriendCandidateDev />*/}
      </div>
      hello
      {/*<ParticipatedChatRoomContextMenu />*/}
    </div>
  )
}
