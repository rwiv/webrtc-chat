package com.github.cloverchatserver.domain.chatmsg.api.data

import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomDto
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import java.io.Serializable
import java.time.LocalDateTime

data class StompChatMessage(
    val id: Long,
    val chatRoom: ChatRoomDto,
    val createUser: AccountResponse,
    val content: String,
    val createAt: LocalDateTime
) : Serializable {

    companion object {
        fun of(chatMessage: ChatMessage) = StompChatMessage(
            chatMessage.id!!,
            ChatRoomDto.of(chatMessage.chatRoom),
            AccountResponse.of(chatMessage.createAccount),
            chatMessage.content,
            chatMessage.createAt
        )
    }
}
