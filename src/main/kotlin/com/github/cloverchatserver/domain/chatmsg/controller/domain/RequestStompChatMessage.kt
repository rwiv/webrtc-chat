package com.github.cloverchatserver.domain.chatmsg.controller.domain

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.chatmsg.repository.ChatMessage
import com.github.cloverchatserver.domain.user.repository.Account
import java.time.LocalDateTime

data class RequestStompChatMessage(
    val chatRoomId: Long,
    val createUserId: Long,
    val content: String
) {

    fun toChatMessage(chatRoom: ChatRoom, createAccount: Account): ChatMessage {
        return ChatMessage(null, chatRoom, createAccount, content, LocalDateTime.now())
    }
}
