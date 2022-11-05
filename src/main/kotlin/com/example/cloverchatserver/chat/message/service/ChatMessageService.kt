package com.example.cloverchatserver.chat.message.service

import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.controller.domain.ResponseStompChatMessage
import com.example.cloverchatserver.chat.message.repository.ChatMessage

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage): ChatMessage
}