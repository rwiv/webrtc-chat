package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestChatMessagesReadForm
import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestStompChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser

interface ChatMessageService {

    fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage>

    fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage
}