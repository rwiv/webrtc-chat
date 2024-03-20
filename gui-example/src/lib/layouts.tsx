import {ReactNode} from "react";

interface Props {
  children: ReactNode;
  className?: string;
}

function Flex({ children, className }: Props) {
  return (
    <div className={className} css={{ display: "flex" }}>
      {children}
    </div>
  )
}

function VStack({ children, className }: Props) {
  return (
    <div className={className} css={{
      display: "flex",
      flexDirection: "column",
      gap: 8,
    }}>
      {children}
    </div>
  )
}

function HStack({ children, className }: Props) {
  return (
    <div className={className} css={{
      display: "flex",
      flexDirection: "row",
      flexWrap: "wrap",
      gap: 8,
    }}>
      {children}
    </div>
  )
}

function Center({ children, className }: Props) {
  return (
    <div className={className} css={{
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
    }}>
      {children}
    </div>
  )
}

export { Flex, VStack, HStack, Center };