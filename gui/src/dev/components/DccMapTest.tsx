import {useDccMapStore} from "@/hooks/chatmessage/useDccMapStore.ts";
import {useEffect} from "react";
import {Params} from "react-router";

interface DccMapTestProps {
  params: Readonly<Params<string>>,
}

export function DccMapTest({ params }: DccMapTestProps) {

  const {dccMap, refresh: refreshDccMap} = useDccMapStore();

  useEffect(() => {
    setInterval(() => {
      refreshDccMap();
    }, 1000);
  }, [params]);

  return (
    <div>
      {dccMap.values().map(((dcc, idx) => (
        <div key={idx} className="border-2 w-36">
          <div>{dcc.targetId}</div>
          <div>my: {dcc.myChannel.readyState}</div>
          <div>your: {dcc.yourChannel?.readyState}</div>
        </div>
      )))}
    </div>
  )
}