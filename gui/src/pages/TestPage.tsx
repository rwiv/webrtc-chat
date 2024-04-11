import {
  ContextMenuItem, ContextMenuShortcut,
} from "@/components/ui/context-menu"
import * as ContextMenu from '@radix-ui/react-context-menu';
import {cn} from "@/lib/utils.ts";
import {LogOut} from "lucide-react";

export function TestPage() {
  return (
    <div>
      <ParticipatedChatRoomContextMenu />
    </div>
  )
}

function ParticipatedChatRoomContextMenu() {
  return (
    <ContextMenu.Root>
      <ContextMenu.Trigger
        css={{
          width: "4rem", height: "4rem", margin: "2rem", padding: "10rem",
      }}
        // className="flex h-[150px] w-[300px] items-center justify-center rounded-md border border-dashed text-sm"
      >
        Right click here
      </ContextMenu.Trigger>
      <ContextMenu.Portal>
        <ContextMenu.Content
          className={cn(
            "z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-md"
          )}
          css={{width: "15rem"}}
        >
          <ContextMenuItem>
            <LogOut className="mr-2 h-4 w-4"/>
            <div>
              채팅방 나가기
            </div>
            <ContextMenuShortcut>⌘[</ContextMenuShortcut>
          </ContextMenuItem>
          <ContextMenuItem>
            <LogOut className="mr-2 h-4 w-4"/>
            채팅방 삭제
            <ContextMenuShortcut>⌘[</ContextMenuShortcut>
          </ContextMenuItem>
        </ContextMenu.Content>
      </ContextMenu.Portal>
    </ContextMenu.Root>
  );
};
