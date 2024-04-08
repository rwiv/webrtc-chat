package com.github.cloverchatserver.domain.chatmessage.api.event

import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage

data class ChatMessageCreationEvent(
    val chatMessage: ChatMessage,
)
