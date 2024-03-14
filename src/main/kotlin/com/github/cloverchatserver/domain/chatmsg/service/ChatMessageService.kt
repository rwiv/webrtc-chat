package com.github.cloverchatserver.domain.chatmsg.service

import com.github.cloverchatserver.domain.chatmsg.controller.domain.RequestChatMessagesReadForm
import com.github.cloverchatserver.domain.chatmsg.controller.domain.RequestStompChatMessage
import com.github.cloverchatserver.domain.chatmsg.repository.ChatMessage
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage
}