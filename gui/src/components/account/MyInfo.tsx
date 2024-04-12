import {
  DropdownMenu,
  DropdownMenuContent, DropdownMenuGroup, DropdownMenuItem,
  DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuShortcut,
  DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.tsx";
import {LogOut, Settings, User} from "lucide-react";
import {logout} from "@/client/account.ts";
import {useNavigate} from "react-router";
import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {css} from "@emotion/react";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";

const userInfoStyle = css`
  display: flex;
  align-items: center;
  padding: 10px;
  color: white;
`;

export function MyInfo() {

  const {myInfo} = useMyInfo();

  const navigate = useNavigate();

  const onLogout = async () => {
    await logout();
    navigate("/account-select");
  }

  return (
    <div css={userInfoStyle}>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <button>
            <img
              src={`${consts.endpoint}${myInfo?.avatarUrl}`}
              css={iconStyle}
              alt="my-avatar"
            />
          </button>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-56">
          <DropdownMenuLabel>My Account</DropdownMenuLabel>
          <DropdownMenuSeparator/>
          <DropdownMenuGroup>
            <DropdownMenuItem>
              <User className="mr-2 h-4 w-4"/>
              <span>Profile</span>
              <DropdownMenuShortcut>⇧⌘P</DropdownMenuShortcut>
            </DropdownMenuItem>
            <DropdownMenuItem>
              <Settings className="mr-2 h-4 w-4"/>
              <span>Settings</span>
              <DropdownMenuShortcut>⌘S</DropdownMenuShortcut>
            </DropdownMenuItem>
          </DropdownMenuGroup>
          <DropdownMenuSeparator/>
          <DropdownMenuItem onClick={onLogout}>
            <LogOut className="mr-2 h-4 w-4"/>
            <span>Log out</span>
            <DropdownMenuShortcut>⇧⌘Q</DropdownMenuShortcut>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
      <div css={{marginLeft: "0.7rem"}}>
        <div className="font-semibold">{myInfo?.nickname}</div>
        <div>{myInfo?.username}</div>
      </div>
    </div>
  )
}