import {
  Dialog, DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "@/components/ui/dialog.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/ui/input.tsx";
import React, {useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {ChatRoom} from "@/graphql/types.ts";
import {useNavigate} from "react-router";
import {useCreateChatUser} from "@/client/chatUser.ts";
import {useCurChatRoom} from "@/hooks/global/useCurChatRoom.ts";
import {useSidebarState} from "@/hooks/global/useSidebarState.ts";
import {ApolloError} from "@apollo/client";
import {HStack} from "@/lib/style/layouts.tsx";

interface PasswordInputDialogProps {
  openRef: React.RefObject<HTMLButtonElement>;
  closeRef: React.RefObject<HTMLButtonElement>;
  chatRoom: ChatRoom | undefined;
}

export function PasswordInputDialog({openRef, closeRef, chatRoom}: PasswordInputDialogProps) {

  const navigate = useNavigate();

  const {createChatUser} = useCreateChatUser();
  const {setCurChatRoom} = useCurChatRoom();
  const {setSidebarState} = useSidebarState();

  const [passwordInput, setPasswordInput] = useState("");
  const [isFailed, setIsFailed] = useState(false);

  const onSubmit = async () => {
    if (chatRoom === undefined) return;

    try {
      const res = await createChatUser({variables: {
          chatRoomId: chatRoom.id,
          password: passwordInput,
      }});
      console.log(res.data);

      setCurChatRoom(chatRoom);
      setSidebarState("CHATROOM");
      navigate(`/chat-rooms/${chatRoom.id}`);
    } catch (e) {
      if ((e as ApolloError).graphQLErrors[0].message.includes("invalid password")) {
        console.log("invalid password");
      }
      setIsFailed(true);
      return;
    }
  }

  const onOpenChange = (isOpen: boolean) => {
    if (!isOpen) {
      setIsFailed(false);
    }
  }

  return (
    <div css={{height: 0}}>
      <Dialog onOpenChange={onOpenChange}>
        <DialogTrigger ref={openRef} />
        <DialogClose ref={closeRef} />
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>비밀번호 입력</DialogTitle>
          </DialogHeader>
          <HStack>
            <Label css={{
              alignContent: "top",
              marginTop: "0.7rem"
            }}>
              비밀번호
            </Label>
            <div css={{flexGrow: 1}}>
              <Input
                onChange={e => setPasswordInput(e.target.value)}
                value={passwordInput}
              />
              <div css={{
                color: "#dc2626",
                visibility: isFailed ? "visible" : "hidden",
                marginLeft: "0.5rem",
                marginTop: "0.5rem",
              }}>
                잘못된 비밀번호입니다.
              </div>
            </div>
          </HStack>
          <DialogFooter>
            <Button type="submit" onClick={onSubmit}>입장</Button>
            <DialogClose asChild>
            <Button variant="outline">취소</Button>
            </DialogClose>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}
