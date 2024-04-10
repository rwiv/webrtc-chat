import {create} from 'zustand';

export type SidebarState = "FRIEND" | "CHATROOM" | "SEARCH";

interface GlobalState {
  sidebarState: SidebarState;
  setSidebarState: (sidebarState: SidebarState) => void;
}

export const useSidebarState = create<GlobalState>((set) => ({
  sidebarState: "CHATROOM",
  setSidebarState: (sidebarState) => set(() => ({ sidebarState})),
}));
