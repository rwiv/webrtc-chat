package com.github.cloverchatserver.domain.chatmsg.api.domain

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.account.persistence.Account
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
