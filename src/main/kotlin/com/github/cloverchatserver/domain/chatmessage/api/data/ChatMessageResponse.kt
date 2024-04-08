package com.github.cloverchatserver.domain.chatmessage.api.data

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage
import java.time.LocalDateTime

data class ChatMessageResponse(
    val id: Long,
    val chatRoomId: Long,
    val createdBy: AccountResponse,
    val content: String,
    val createdAt: LocalDateTime,
    val num: Int,
) {
    companion object {
        fun of(chatMessage: ChatMessage) = ChatMessageResponse(
            id = chatMessage.id!!,
            chatRoomId = chatMessage.chatRoom.id!!,
            createdBy = AccountResponse.of(chatMessage.createdBy),
            content = chatMessage.content,
            createdAt = chatMessage.createdAt,
            num = chatMessage.num,
        )
    }
}
