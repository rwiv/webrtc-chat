package com.example.cloverchatserver.chat.service

import com.example.cloverchatserver.chat.controller.RequestChatMessage
import com.example.cloverchatserver.chat.repository.ChatMessage

interface ChatMessageService {

    fun getChatMessagesBy(chatRoomId: Long): List<ChatMessage>

    fun createChatMessage(requestChatMessage: RequestChatMessage): ChatMessage
}