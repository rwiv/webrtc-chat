package com.github.cloverchatserver.domain.chatroom.business.data

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.account.persistence.Account
import java.io.Serializable
import java.time.LocalDateTime

data class ChatRoomCreation(
    val createUserId: Long,
    val password: String?,
    val title: String,
    val type: ChatRoomType
): Serializable {

    fun toChatRoom(createBy: Account): ChatRoom {
        return ChatRoom(null, createBy, password, title, LocalDateTime.now(), type)
    }
}
