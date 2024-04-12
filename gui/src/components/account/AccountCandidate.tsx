import {Account} from "@/graphql/types.ts";
import {consts} from "@/configures/consts.ts";
import {iconStyle} from "@/styles/globalStyles.ts";
import {Button} from "@/components/ui/button.tsx";

interface AccountCandidateProps {
  account: Account;
  onSubmit: () => void;
}

export function AccountCandidate({ account, onSubmit }: AccountCandidateProps) {
  return (
    <div className=" flex items-center space-x-4 rounded-md border p-4">

      <img
        src={`${consts.endpoint}${account.avatarUrl}`}
        css={iconStyle}
        alt="my-avatar"
      />
      <div className="flex-1 space-y-1">
        <p className="text-sm font-medium leading-none">
          {account.nickname}
        </p>
        <p className="text-sm text-muted-foreground">
          {account.username}
        </p>
      </div>
      <Button type="submit" onClick={onSubmit}>추가</Button>
    </div>
  )
}
