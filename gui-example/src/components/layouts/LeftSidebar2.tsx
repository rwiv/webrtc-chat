import {ChatRoomSidebar} from "@/components/chatroom/ChatRoomSidebar.tsx";
import {css} from "@emotion/react";
import {useState} from "react";
import {Center} from "@/lib/layouts.tsx";
import { MdOutlineGroup } from "react-icons/md";
import { MdOutlineMessage } from "react-icons/md";
import { MdOutlineSearch } from "react-icons/md";
import {mq} from "@/lib/mediaQueryHelpers.ts";

const navSidebarStyle = css`
    //width: 4.5%;
    display: flex;
    flex-direction: column;
    //padding: 0;
    background-color: #222831;
`;

const columnStyle = css`
    display: flex;
    flex-direction: column;
    //width: 17.5%;
    overflow-y: auto;
    background-color: #31363f;
    //height: 100%;
`;

type SidebarState = "FRIEND" | "CHATROOM" | "SEARCH";

const left = mq.m_all(2,2,3,3,3,3);
const right = mq.m_all(10,10,9, 9,9,9);

const iconSize = "2.1rem";
const iconColor = "#ffffff";

export function LeftSideBar2() {

  const [curState, setCurState] = useState<SidebarState>("SEARCH");

  return (
    <>
      <div className="column" css={[navSidebarStyle, left]}>
        <button onClick={() => setCurState("FRIEND")}>
          <Center className="mt-5">
            <MdOutlineGroup size={iconSize} color={iconColor} />
          </Center>
        </button>
        <button onClick={() => setCurState("CHATROOM")}>
          <Center className="mt-5">
            <MdOutlineMessage size={iconSize} color={iconColor} />
          </Center>
        </button>
        <button onClick={() => setCurState("SEARCH")}>
          <Center className="mt-5">
            <MdOutlineSearch size={iconSize} color={iconColor} />
          </Center>
        </button>
      </div>
      <div css={[columnStyle, right]}>
        {curState === "SEARCH" && <ChatRoomSidebar/>}
      </div>
    </>
  )
}
