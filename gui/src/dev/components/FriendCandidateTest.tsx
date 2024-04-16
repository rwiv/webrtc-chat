import {useMyInfo} from "@/hooks/common/useMyInfo.ts";
import {AccountCandidate} from "@/components/account/AccountCandidate.tsx";

export function FriendCandidateTest() {
  const {myInfo} = useMyInfo();

  return (
    <>
      {myInfo && (
        <AccountCandidate account={myInfo} onSubmit={() => {}} />
      )}
    </>
  )
}
