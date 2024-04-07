package com.github.cloverchatserver.common.webrtc

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/signal")
class SignalingController(
    private val template: SimpMessagingTemplate,
) {

    @PostMapping("/offer/{chatRoomId}")
    fun offer(@PathVariable chatRoomId: String, @RequestBody desc: RTCSessionDescription) {
        template.convertAndSend("/sub/signal/offer/${chatRoomId}", desc)
    }

    @PostMapping("/answer/{chatRoomId}")
    fun answer(@PathVariable chatRoomId: String, @RequestBody message: String) {
        template.convertAndSend("/sub/signal/answer/${chatRoomId}", message)
    }

    @PostMapping("/candidate/{chatRoomId}")
    fun candidate(@PathVariable chatRoomId: String, @RequestBody message: String) {
        template.convertAndSend("/sub/signal/candidate/${chatRoomId}", message)
    }
}