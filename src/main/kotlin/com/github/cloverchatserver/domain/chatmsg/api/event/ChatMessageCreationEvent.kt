package com.github.cloverchatserver.domain.chatmsg.api.event

import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage

data class ChatMessageCreationEvent(
    val chatMessage: ChatMessage,
)
