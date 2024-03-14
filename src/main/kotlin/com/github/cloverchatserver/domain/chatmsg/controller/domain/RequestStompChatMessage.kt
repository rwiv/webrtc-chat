package com.github.cloverchatserver.domain.chatmsg.controller.domain

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.chatmsg.repository.ChatMessage
import com.github.cloverchatserver.domain.user.repository.User
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
