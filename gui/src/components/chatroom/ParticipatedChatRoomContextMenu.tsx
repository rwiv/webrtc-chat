import * as ContextMenu from "@radix-ui/react-context-menu";
import {cn} from "@/lib/utils.ts";
import {ContextMenuItem, ContextMenuShortcut} from "@/components/ui/context-menu.tsx";
import {LogOut, Trash2} from "lucide-react";
import React from "react";
import {useCurChatRoomStore} from "@/hooks/chatroom/useCurChatRoomStore.ts";
import {myChatUsersQL, useDeleteChatUserMe} from "@/client/chatUser.ts";
import {useDeleteChatRoom} from "@/client/chatRoom.ts";
import {useNavigate} from "react-router";
import {Account, ChatRoom} from "@/graphql/types.ts";

interface ParticipatedChatRoomContextMenuProps {
  chatRoom: ChatRoom;
  myInfo: Account;
  children: React.ReactNode;
}

export function ParticipatedChatRoomContextMenu({ chatRoom, myInfo, children }: ParticipatedChatRoomContextMenuProps) {

  const navigate = useNavigate();

  const {setCurChatRoom} = useCurChatRoomStore();
  const {deleteChatUserMe} = useDeleteChatUserMe();
  const {deleteChatRoom} = useDeleteChatRoom();

  const onExit = async () => {
    const variables = { chatRoomId: chatRoom.id };
    const res = await deleteChatUserMe({
      variables,
      refetchQueries: [ myChatUsersQL ],
    })
    console.log(res);
    navigate("/");
  }

  const onDeleteChatRoom = async () => {
    const variables = { chatRoomId: chatRoom.id };
    const res = await deleteChatRoom({
      variables,
      refetchQueries: [ myChatUsersQL ],
    });
    console.log(res.data?.deleteChatRoom);
    setCurChatRoom(null);
    navigate("/");
  }

  const isMyChatRoom = () => {
    return chatRoom.createdBy.id === myInfo.id;
  }

  return (
    <ContextMenu.Root>
      <ContextMenu.Trigger>
        {children}
      </ContextMenu.Trigger>
      <ContextMenu.Portal>
        <ContextMenu.Content
          className={cn(
            "z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-md"
          )}
          css={{width: "15rem"}}
        >
          <ContextMenuItem
            disabled={!isMyChatRoom()}
            onClick={onDeleteChatRoom}
          >
            <Trash2 className="mr-2 h-4 w-4"/>
            채팅방 삭제
            <ContextMenuShortcut>⌘R</ContextMenuShortcut>
          </ContextMenuItem>
          <ContextMenuItem onClick={onExit}>
            <LogOut className="mr-2 h-4 w-4"/>
            <div>
              채팅방 나가기
            </div>
            <ContextMenuShortcut>⌘Q</ContextMenuShortcut>
          </ContextMenuItem>
        </ContextMenu.Content>
      </ContextMenu.Portal>
    </ContextMenu.Root>
  );
}
