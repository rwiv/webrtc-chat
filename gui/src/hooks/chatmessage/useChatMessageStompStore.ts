import {create} from 'zustand';
import {Client, StompSubscription} from "@stomp/stompjs";

export class StompClient {

  constructor(
    public stomp: Client | undefined,
    public subs: StompSubscription[],
  ) {
  }

  isInit() {
    return this.stomp === undefined && this.subs.length === 0;
  }

  async close() {
    this.subs.forEach(it => {
      it.unsubscribe();
    });
    await this.stomp?.deactivate();
  }
}

interface GlobalState {
  stompClient: StompClient;
  setNewStompClient: (client: Client | undefined, subs: StompSubscription[]) => void;
}

export const useChatMessageStompStore = create<GlobalState>((set) => ({
  stompClient: new StompClient(undefined, []),
  setNewStompClient: (client, subs) => set(prev => {
    const stompClient = prev.stompClient;
    stompClient?.close();
    stompClient.stomp = client;
    stompClient.subs = subs;
    return { ...prev, stompClient };
  }),
}));
