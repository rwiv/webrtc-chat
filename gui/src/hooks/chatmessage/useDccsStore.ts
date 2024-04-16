import {create} from 'zustand';
import {Account} from "@/graphql/types.ts";

export class DccMap {

  constructor(public current: Map<number, DataChannelConnection> = new Map()) {
  }

  add(dcc: DataChannelConnection) {
    const targetId = dcc.target.id;
    const dup = this.get(targetId);
    if (dup !== undefined) {
      console.log("duplicated");
      console.log(dup);
    }
    this.current.set(targetId, dcc);
  }

  restore() {
    this.close();
    this.current = new Map();
  }

  close() {
    for (const dcc of this.values()) {
      dcc.close();
    }
  }

  get(key: number): DataChannelConnection | undefined {
    return this.current.get(key);
  }

  values(): DataChannelConnection[] {
    const result = [];
    for (const dcc of this.current.values()) {
      result.push(dcc);
    }
    return result;
  }
}

export class DataChannelConnection {
  constructor(
    public readonly connection: RTCPeerConnection,
    public readonly myChannel: RTCDataChannel,
    public readonly target: Account,
    public yourChannel: RTCDataChannel | null = null,
  ) {
  }

  setYourChannel(yourChannel: RTCDataChannel) {
    this.yourChannel = yourChannel;
  }

  isConnected() {
    return this.getOpenChannel() !== null;
  }

  getOpenChannel() {
    if (this.myChannel.readyState === "open") {
      return this.myChannel;
    } else if (this.yourChannel?.readyState === "open") {
      return this.yourChannel;
    } else {
      return null;
    }
  }

  setListener() {
  }

  close() {
    this.myChannel.close();
    this.yourChannel?.close();
    this.connection.close();
  }
}

interface GlobalState {
  dccMap: DccMap;
  addDcc: (dcc: DataChannelConnection) => void;
  restore: () => void;
  refresh: () => void;
}

export const useDccsStore = create<GlobalState>((set) => ({
  dccMap: new DccMap(),
  addDcc: dcc => set(prev => {
    const dccMap = prev.dccMap;
    dccMap.add(dcc);
    return { ...prev, dccMap };
  }),
  restore: () => set(prev => {
    const dccMap = prev.dccMap;
    dccMap.restore();
    return { ...prev, dccMap };
  }),
  refresh: () => set(prev => ({ ...prev })),
}));

