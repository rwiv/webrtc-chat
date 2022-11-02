package com.example.cloverchatserver.chat.controller

import com.example.cloverchatserver.chat.service.ChatMessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.lang.RuntimeException

@Controller
class ChatMessageStompController(
    val chatMessageService: ChatMessageService
) {

    @MessageMapping("/{chatRoomId}")
    @SendTo("/topic/{chatRoomId}")
    fun greeting(
        requestChatMessage: RequestChatMessage,
        @DestinationVariable chatRoomId: Long
    ): ResponseChatMessage {
        if (requestChatMessage.chatRoomId != chatRoomId) {
            throw RuntimeException("chatRoomId is different")
        }

        return chatMessageService
            .createChatMessage(requestChatMessage)
            .toResponseChatMessage()
    }
}