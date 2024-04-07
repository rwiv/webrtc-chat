package com.github.cloverchatserver.common.webrtc

data class RTCSessionDescription(
    val type: String,
    val sdp: String,
    val sentBy: Long,
) {

    enum class DescriptionType {
        OFFER,
        ANSWER,
    }

    fun getType(): DescriptionType {
        return when(type) {
            "offer" -> DescriptionType.OFFER
            "answer" -> DescriptionType.ANSWER
            else -> throw RuntimeException("not supported description type")
        }
    }
}
