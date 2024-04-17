import {create} from 'zustand';
import {ChatRoom} from "@/graphql/types.ts";

interface GlobalState {
  curChatRoom: ChatRoom | null;
  setCurChatRoom: (chatRoom: ChatRoom | null) => void;
  curChatRoomId: number | null;
  setCurChatRoomId: (chatRoomId: number | null) => void;
}

export const useCurChatRoomStore = create<GlobalState>((set) => ({
  curChatRoom: null,
  setCurChatRoom: chatRoom => set(() => ({ curChatRoom: chatRoom })),
  curChatRoomId: null,
  setCurChatRoomId: chatRoomId => set(() => ({ curChatRoomId: chatRoomId })),
}));
