package com.example.cloverchatserver.chat.controller

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.chat.repository.ChatMessage
import com.example.cloverchatserver.user.repository.User
import java.time.LocalDateTime

data class RequestChatMessage(
    val chatRoomId: Long,
    val createUserId: Long,
    val content: String
) {

    fun toChatMessage(chatRoom: ChatRoom, createUser: User): ChatMessage {
        return ChatMessage(null, chatRoom, createUser, content, LocalDateTime.now())
    }
}
