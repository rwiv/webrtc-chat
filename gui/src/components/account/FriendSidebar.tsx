import {useMyFriends} from "@/client/account.ts";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";

export function FriendSidebar() {
  const {data} = useMyFriends();

  return (
    <div>
      {data?.account?.friends?.map(it => (
        <div key={it.to?.id}>
          <img
            src={`${consts.endpoint}${it.to?.avatarUrl}`}
            css={iconStyle}
            alt="sender-avatar"
          />
          {it.to?.nickname}
        </div>
      ))}
    </div>
  )
}
