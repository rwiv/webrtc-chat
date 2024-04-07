import {Client} from "@stomp/stompjs";
import {consts} from "@/configures/consts.ts";

export function createStompClient() {
  return new Client({
    brokerURL: `ws://${consts.domain}/stomp`,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });
}
