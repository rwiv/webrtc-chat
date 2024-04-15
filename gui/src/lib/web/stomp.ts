import {Client} from "@stomp/stompjs";
import {consts} from "@/configures/consts.ts";

const protocol = consts.isDev ? "ws" : "wss";

export function createStompClient() {
  return new Client({
    brokerURL: `${protocol}://${consts.domain}/stomp`,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });
}
