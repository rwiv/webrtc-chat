package com.github.cloverchatserver.common.webrtc

data class DescriptionMessage(
    val description: RTCSessionDescription,
    val senderId: Long,
)
