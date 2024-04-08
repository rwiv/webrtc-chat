package com.github.cloverchatserver.domain.chatmessage.api

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.security.Principal

//@Controller
class ChatMessageStompController(
    private val template: SimpMessagingTemplate,
) {

    @MessageMapping("/message/{chatRoomId}")
    fun chatMessageHandle(
        message: String,
        authentication: Principal,
        @DestinationVariable chatRoomId: Long
    ) {
        println(message)
        template.convertAndSend("/sub/message/${chatRoomId}", message)
    }
}
