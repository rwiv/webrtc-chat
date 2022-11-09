package com.example.cloverchatserver.chat.message.controller

import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.controller.domain.ResponseStompChatMessage
import com.example.cloverchatserver.chat.message.service.ChatMessageService
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping("/chat/message")
class ChatMessageHttpController(val chatMessageService: ChatMessageService) {

    @PostMapping("/list")
    fun getChatMessages(@RequestBody form: RequestChatMessagesReadForm): List<ResponseStompChatMessage> {
        return chatMessageService.getChatMessagesBy(form)
            .map { chatMessage -> chatMessage.toResponseStompChatMessage() }
    }

    @PostMapping("/create/{chatRoomId}")
    fun createChatMessage(@PathVariable chatRoomId: Long,
                          @RequestBody requestStompChatMessage: RequestStompChatMessage,
                          authentication: Authentication): ResponseStompChatMessage {

        if (requestStompChatMessage.chatRoomId != chatRoomId) {
            throw RuntimeException("chatRoomId is different")
        }

        val responseUser = authentication.details as ResponseUser

        return chatMessageService.createChatMessage(requestStompChatMessage, responseUser)
            .toResponseStompChatMessage()
    }
}