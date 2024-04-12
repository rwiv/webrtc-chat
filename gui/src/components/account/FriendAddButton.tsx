import {
  Dialog, DialogClose,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "@/components/ui/dialog.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Label} from "@/components/ui/label.tsx";
import {useRef, useState} from "react";
import {Center, HStack} from "@/lib/style/layouts.tsx";
import {MdAddCircle} from "react-icons/md";
import {myFriendsQL, useAddFriend} from "@/client/friend.ts";
import {Input} from "@/components/ui/input.tsx";
import {useApolloClient} from "@apollo/client";
import {Account, Query} from "@/graphql/types.ts";
import {getAccountByUsername} from "@/client/account.ts";
import {getQueryName} from "@/lib/web/apollo.ts";
import {AccountCandidate} from "@/components/account/AccountCandidate.tsx";

interface FriendAddButtonProps {
  myInfo: Account;
}

export function FriendAddButton({ myInfo }: FriendAddButtonProps) {

  const client = useApolloClient();

  const closeRef = useRef<HTMLButtonElement>(null);
  const [emailInput, setEmailInput] = useState("");
  const {addFriend} = useAddFriend();

  const [searchResult, setSearchResult] = useState<Account>();
  const [isSearched, setIsSearched] = useState(false);

  const onSubmit = async () => {
    if (searchResult === undefined) return;

    const variables = {
      fromAccountId: myInfo.id,
      toAccountId: searchResult.id,
    };
    const res = await addFriend({ variables });
    console.log(res.data?.addFriend);
    await client.refetchQueries({ include: [getQueryName(myFriendsQL)] });

    closeRef.current?.click();
  }

  const onSearch = async () => {
    const res = await client.query<Query>({
      query: getAccountByUsername,
      variables: { username: emailInput },
      fetchPolicy: "network-only",
    });
    setSearchResult(res.data.account ?? undefined);
    setIsSearched(true);
  }

  const onOpenChange = (isOpen: boolean) => {
    if (!isOpen) {
      setSearchResult(undefined);
      setIsSearched(false);
    }
  }

  return (
    <Dialog onOpenChange={onOpenChange}>
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
          <DialogTitle>친구 추가</DialogTitle>
        </DialogHeader>
        <Center>
          <HStack>
            <Label css={{alignContent: "center"}}>
              이메일
            </Label>
            <div>
              <Input
                onChange={e => setEmailInput(e.target.value)}
                value={emailInput}
              />
            </div>
            <Button variant="outline" onClick={onSearch}>검색</Button>
          </HStack>
        </Center>
        {isSearched ? (
          searchResult ? (
            <AccountCandidate account={searchResult} onSubmit={onSubmit}/>
          ) : (
            <div>해당 유저는 존재하지 않습니다.</div>
          )
        ) : (
          <div css={{margin: "1rem"}} />
        )}
      </DialogContent>
    </Dialog>
  )
}
