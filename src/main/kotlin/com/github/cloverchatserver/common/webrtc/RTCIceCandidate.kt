package com.github.cloverchatserver.common.webrtc

data class RTCIceCandidate(
//    val address: String,
    val candidate: String,
//    val component: String,
//    val foundation: String,
//    val port: Int,
//    val priority: Int,
//    val protocol: String,
//    val relatedAddress: String,
//    val relatedPort: Int,
    val sdpMLineIndex: Int,
    val sdpMid: String,
//    val tcpType: String?,
//    val type: String,
//    val url: String?,
    val usernameFragment: String,
) {
//    enum class CandidateType {
//        HOST,
//        SRFLX,
//        PRFLX,
//        RELAY,
//    }
//
//    fun getType() {
//        when(type) {
//            "host" -> CandidateType.HOST
//            "srflx" -> CandidateType.SRFLX
//            "prflx" -> CandidateType.PRFLX
//            "relay" -> CandidateType.RELAY
//            else -> throw RuntimeException("not supported candidate type")
//        }
//    }
}
