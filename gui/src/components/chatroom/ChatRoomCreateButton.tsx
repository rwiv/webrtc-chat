import {
  Dialog, DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "@/components/ui/dialog.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/ui/input.tsx";
import React, {useRef, useState} from "react";
import {ChatRoom, ChatRoomCreateRequest, ChatRoomType} from "@/graphql/types.ts";
import {useCreateChatRoom} from "@/client/chatRoom.ts";
import { MdAddCircle } from "react-icons/md";
import {Center} from "@/lib/style/layouts.tsx";
import { Checkbox } from "@/components/ui/checkbox"

interface ChatRoomCreateButtonProps {
  addChatRoom: (chatRoom: ChatRoom) => void;
}

export function ChatRoomCreateButton({ addChatRoom }: ChatRoomCreateButtonProps) {

  const ref = useRef<HTMLButtonElement>(null);
  const [chatRoomInput, setChatRoomInput] = useState("");
  const {createChatRoom} = useCreateChatRoom();
  const [isPrivate, setIsPrivate] = useState(false);
  const [password, setPassword] = useState("");

  const handleCheckboxChange = (checked: boolean) => {
    setIsPrivate(checked); 
    if (!checked) setPassword(''); 
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    setState: React.Dispatch<React.SetStateAction<string>>,
  ) => {
    setState(e.target.value);
  };

  const onAddChatRoom = async () => {
    const variables: {req: ChatRoomCreateRequest} = {
      req: {
        password: isPrivate ? password : null,
        title: chatRoomInput,
        type: isPrivate ? ChatRoomType.Private : ChatRoomType.Public,
      },
    };
    const res = await createChatRoom({variables});
    const created = res.data?.createChatRoom;
    console.log(created)
    if (created === undefined) {
      throw Error("created chat room is undefined");
    }
    setChatRoomInput("");
    setIsPrivate(false);
    setPassword("");
    ref?.current?.click();
    addChatRoom(created);
  }

  return (
    <Dialog onOpenChange={() => true}>
      <DialogTrigger asChild>
        <button>
          <Center>
            <MdAddCircle size="2rem" color="#ffffff" />
          </Center>
        </button>
      </DialogTrigger>
      <DialogClose ref={ref}></DialogClose>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>채팅방 만들기</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-4 items-center gap-4">
            <Label htmlFor="name" className="text-right">
              이름
            </Label>
            <Input
              id="name"
              defaultValue=""
              className="col-span-3"
              onChange={(e: any) => handleChange(e, setChatRoomInput)}
              value={chatRoomInput}
            />
          </div>
        </div>
        <div className="flex items-center space-x-2">
          <Checkbox id="terms" 
            checked={isPrivate}
            onCheckedChange={handleCheckboxChange}/>
            <label
              htmlFor="terms"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            >
              비밀방으로 만들기
            </label>
        </div>
        {isPrivate && (
          <div className="grid grid-cols-4 items-center gap-4">
            <Label htmlFor="password" className="text-right">
              비밀번호
            </Label>
            <Input
              id="password"
              type="password"
              className="col-span-3"
              value={password}
              onChange={handlePasswordChange}
            />
          </div>
        )}
        <DialogFooter>
          <Button type="submit" onClick={() => onAddChatRoom()}>채팅방 만들기</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}
