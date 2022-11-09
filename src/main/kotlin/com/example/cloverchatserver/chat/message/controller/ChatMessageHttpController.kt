package com.example.cloverchatserver.chat.message.controller

import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.service.ChatMessageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat/message")
class ChatMessageHttpController(val chatMessageService: ChatMessageService) {

    @PostMapping("/list")
    fun getChatMessages(@RequestBody form: RequestChatMessagesReadForm) =
        chatMessageService.getChatMessagesBy(form)
            .map { chatMessage -> chatMessage.toResponseStompChatMessage() }
}