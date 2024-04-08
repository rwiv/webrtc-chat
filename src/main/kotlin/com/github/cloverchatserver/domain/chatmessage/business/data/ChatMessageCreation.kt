package com.github.cloverchatserver.domain.chatmessage.business.data

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage
import com.github.cloverchatserver.domain.account.persistence.Account
import java.time.LocalDateTime

data class ChatMessageCreation(
    val chatRoomId: Long,
    val createUserId: Long,
    val content: String
) {
    fun toChatMessage(chatRoom: ChatRoom, createdBy: Account, num: Int): ChatMessage {
        return ChatMessage(null, chatRoom, createdBy, content, LocalDateTime.now(), num)
    }
}
