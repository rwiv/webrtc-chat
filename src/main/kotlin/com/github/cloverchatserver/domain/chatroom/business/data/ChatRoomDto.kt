package com.github.cloverchatserver.domain.chatroom.business.data

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import java.io.Serializable
import java.time.LocalDateTime

data class ChatRoomDto(
    val id: Long,
    val createUser: AccountResponse,
    val title: String,
    val createDate: LocalDateTime,
    val type: ChatRoomType
): Serializable {

    companion object {
        fun of(chatRoom: ChatRoom) = ChatRoomDto(
            chatRoom.id!!,
            AccountResponse.of(chatRoom.createdBy),
            chatRoom.title,
            chatRoom.createdAt,
            chatRoom.type,
        )
    }
}
