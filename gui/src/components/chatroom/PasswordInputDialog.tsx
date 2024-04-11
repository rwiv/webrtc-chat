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

interface PasswordInputDialogProps {
  onSubmit: (passwordInput: string) => void;
  openRef: React.RefObject<HTMLButtonElement>;
  closeRef: React.RefObject<HTMLButtonElement>;
}

export function PasswordInputDialog({onSubmit, openRef, closeRef}: PasswordInputDialogProps) {

  const [passwordInput, setPasswordInput] = useState("");

  const onClickSubmitButton = () => {
    onSubmit(passwordInput);
    closeRef.current?.click();
  }

  return (
    <div css={{height: 0}}>
      <Dialog onOpenChange={() => true}>
        <DialogTrigger asChild>
          <button ref={openRef} />
        </DialogTrigger>
        <DialogClose ref={closeRef} />
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>비밀번호 입력</DialogTitle>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="name" className="text-right">
                비밀번호
              </Label>
              <Input
                id="name"
                defaultValue=""
                className="col-span-3"
                onChange={e => setPasswordInput(e.target.value)}
                value={passwordInput}
              />
            </div>
          </div>
          <DialogFooter>
            <Button type="submit" onClick={onClickSubmitButton}>입장</Button>
            <DialogClose>
              <Button variant="secondary">취소</Button>
            </DialogClose>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}