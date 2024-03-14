package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestChatMessagesReadForm
import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestStompChatMessage
import com.github.cloverchatserver.domain.chatmsg.api.domain.ResponseStompChatMessage
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
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