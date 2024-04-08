import {post} from "@/lib/web/http.ts";
import {consts} from "@/configures/consts.ts";

export interface DescriptionMessage {
  description: RTCSessionDescriptionInit;
  senderId: number;
  receiverId: number;
}

export interface CandidateMessage {
  candidate: RTCIceCandidate;
  senderId: number;
  receiverId: number;
}

export function requestOffer(chatRoomId: number, body: DescriptionMessage) {
  return post(`${consts.endpoint}/api/signal/offer/${chatRoomId}`, body);
}

export function requestAnswer(chatRoomId: number, body: DescriptionMessage) {
  return post(`${consts.endpoint}/api/signal/answer/${chatRoomId}`, body);
}

export function requestCandidate(chatRoomId: number, body: CandidateMessage) {
  return post(`${consts.endpoint}/api/signal/candidate/${chatRoomId}`, body);
}
