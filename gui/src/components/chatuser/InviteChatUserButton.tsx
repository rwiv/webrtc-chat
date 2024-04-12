import {Account, ChatUser} from "@/graphql/types.ts";
import {useRef} from "react";
import {Dialog, DialogClose, DialogContent, DialogHeader, DialogTitle, DialogTrigger} from "@/components/ui/dialog.tsx";
import {Center} from "@/lib/style/layouts.tsx";
import {MdAddCircle} from "react-icons/md";
import {useMyFriends} from "@/client/friend.ts";
import {AccountCandidate} from "@/components/account/AccountCandidate.tsx";
import {useCreateChatUserFromParticipant} from "@/client/chatUser.ts";

interface InviteChatUserButtonProps {
  chatUsers: ChatUser[];
}

export function InviteChatUserButton({ chatUsers }: InviteChatUserButtonProps) {

  const closeRef = useRef<HTMLButtonElement>(null);

  const {data} = useMyFriends();
  const {createChatUserFromParticipant} = useCreateChatUserFromParticipant();

  const onSubmit = async (target: Account) => {
    if (chatUsers.length === 0) return;

    await createChatUserFromParticipant({ variables: {
        chatRoomId: chatUsers[0].chatRoom?.id,
        accountId: target.id,
    }});
    closeRef.current?.click();
  }

  const getTargetFriendAccounts = () => {
    const map = new Map<number, boolean>();
    chatUsers.forEach(it => map.set(it.account.id, true))

    const result: Account[] = [];
    const friendAccounts = data?.account?.friends?.map(it => it.to);
    if (friendAccounts === undefined) return [];
    for (const it of friendAccounts) {
      if (it === undefined || it === null) {
        continue;
      }
      if (map.get(it.id) === undefined) {
        result.push(it);
      }
    }
    return result;
  }

  return (
    <Dialog>
      <DialogTrigger asChild>
        <button>
          <Center>
            <MdAddCircle size="2rem" color="#ffffff" />
          </Center>
        </button>
      </DialogTrigger>
      <DialogClose ref={closeRef}></DialogClose>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>친구 초대</DialogTitle>
        </DialogHeader>
        {getTargetFriendAccounts().map(it =>
          <AccountCandidate account={it} onSubmit={() => onSubmit(it)} />
        )}
      </DialogContent>
    </Dialog>
  )
}
