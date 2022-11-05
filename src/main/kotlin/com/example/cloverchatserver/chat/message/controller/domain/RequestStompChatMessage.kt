package com.example.cloverchatserver.chat.message.controller.domain

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.chat.message.repository.ChatMessage
import com.example.cloverchatserver.user.repository.User
import java.time.LocalDateTime

data class RequestStompChatMessage(
    val chatRoomId: Long,
    val createUserId: Long,
    val content: String
) {

    fun toChatMessage(chatRoom: ChatRoom, createUser: User): ChatMessage {
        return ChatMessage(null, chatRoom, createUser, content, LocalDateTime.now())
    }
}
