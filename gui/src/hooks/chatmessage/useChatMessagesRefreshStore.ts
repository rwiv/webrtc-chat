import {create} from "zustand";

interface GlobalState {
  refreshFlag: string[];
  refresh: () => void;
}

export const useChatMessagesRefreshStore = create<GlobalState>((set) => ({
  refreshFlag: [],
  refresh: () => set(prev => ({ ...prev, refreshFlag: [] })),
}));
