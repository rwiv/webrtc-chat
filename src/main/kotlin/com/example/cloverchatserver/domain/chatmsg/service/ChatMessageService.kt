package com.example.cloverchatserver.domain.chatmsg.service

import com.example.cloverchatserver.domain.chatmsg.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.domain.chatmsg.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.domain.chatmsg.repository.ChatMessage
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage
}