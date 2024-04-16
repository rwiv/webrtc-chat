import {create} from 'zustand';
import {Client, StompSubscription} from "@stomp/stompjs";

interface GlobalState {
  stompClient: StompClient | undefined;
  setNewStompClient: (stompClient: StompClient | undefined) => void;
}

export const useChatMessageStompStore = create<GlobalState>((set) => ({
  stompClient: undefined,
  setNewStompClient: stompClient => set(prev => {
    prev.stompClient?.close();
    return { ...prev, stompClient };
  }),
}));

export class StompClient {

  constructor(
    public stomp: Client,
    public subs: StompSubscription[],
  ) {
  }

  async close() {
    this.subs.forEach(it => {
      it.unsubscribe();
    });
    await this.stomp.deactivate();
  }
}
