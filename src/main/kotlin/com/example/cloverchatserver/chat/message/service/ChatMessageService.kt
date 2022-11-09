package com.example.cloverchatserver.chat.message.service

import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.repository.ChatMessage
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage
}