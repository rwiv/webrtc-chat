import {ChatRoomSidebar} from "@/components/chatroom/ChatRoomSidebar.tsx";
import {css} from "@emotion/react";
import {Center} from "@/lib/style/layouts.tsx";
import { MdOutlineGroup } from "react-icons/md";
import { MdOutlineMessage } from "react-icons/md";
import { MdOutlineSearch } from "react-icons/md";
import {mq} from "@/lib/style/mediaQueries.ts";
import {FriendSidebar} from "@/components/account/FriendSidebar.tsx";
import {ParticipatedChatRoomSidebar} from "@/components/chatroom/ParticipatedChatRoomSidebar.tsx";
import {useSidebarState} from "@/hooks/global/useSidebarState.ts";

const navSidebarStyle = css`
    display: flex;
    flex-direction: column;
    background-color: #222831;
`;

const mainSidebarStyle = css`
    display: flex;
    flex-direction: column;
    background-color: #31363F;
    overflow-y: auto;
`;

const left = mq.m_all(2,2,2, 2, 2, 2);
const right = mq.m_all(10,10, 10, 10, 10, 10);

const iconSize = "2.1rem";
const iconColor = "#ffffff";

export function LeftSidebar() {

  const {sidebarState, setSidebarState} = useSidebarState();

  return (
    <>
      <div css={[navSidebarStyle, left]}>
        <button onClick={() => setSidebarState("FRIEND")}>
          <Center className="mt-5">
            <MdOutlineGroup size={iconSize} color={iconColor}/>
          </Center>
        </button>
        <button onClick={() => setSidebarState("CHATROOM")}>
          <Center className="mt-5">
            <MdOutlineMessage size={iconSize} color={iconColor}/>
          </Center>
        </button>
        <button onClick={() => setSidebarState("SEARCH")}>
          <Center className="mt-5">
            <MdOutlineSearch size={iconSize} color={iconColor}/>
          </Center>
        </button>
      </div>
      <div css={[mainSidebarStyle, right]}>
        {sidebarState === "FRIEND" && <FriendSidebar />}
        {sidebarState === "CHATROOM" && <ParticipatedChatRoomSidebar />}
        {sidebarState === "SEARCH" && <ChatRoomSidebar/>}
      </div>
    </>
  )
}
