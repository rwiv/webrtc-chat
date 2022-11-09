package com.example.cloverchatserver.chat.message.controller

import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.controller.domain.ResponseStompChatMessage
import com.example.cloverchatserver.chat.message.service.ChatMessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.lang.RuntimeException

@Controller
class ChatMessageStompController(val chatMessageService: ChatMessageService) {

    @MessageMapping("/message/{chatRoomId}")
    @SendTo("/sub/message/{chatRoomId}")
    fun chatMessageHandle(requestStompChatMessage: RequestStompChatMessage,
                          @DestinationVariable chatRoomId: Long): ResponseStompChatMessage {

        if (requestStompChatMessage.chatRoomId != chatRoomId) {
            throw RuntimeException("chatRoomId is different")
        }

        return chatMessageService.createChatMessage(requestStompChatMessage)
            .toResponseStompChatMessage()
    }
}