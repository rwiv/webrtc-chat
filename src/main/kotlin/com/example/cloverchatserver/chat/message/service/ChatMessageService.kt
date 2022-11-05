package com.example.cloverchatserver.chat.message.service

import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.controller.domain.ResponseStompChatMessage

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ResponseStompChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage): ResponseStompChatMessage
}