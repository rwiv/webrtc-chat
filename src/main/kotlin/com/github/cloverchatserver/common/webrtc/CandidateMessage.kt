package com.github.cloverchatserver.common.webrtc

data class CandidateMessage(
    val candidate: RTCIceCandidate,
    val senderId: Long,
    val receiverId: Long,
)
