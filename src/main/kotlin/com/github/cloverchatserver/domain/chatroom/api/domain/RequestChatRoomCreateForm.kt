package com.github.cloverchatserver.domain.chatroom.api.domain

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.account.persistence.Account
import java.io.Serializable
import java.time.LocalDateTime

data class RequestChatRoomCreateForm(
    val createUserId: Long,
    val password: String?,
    val title: String,
    val type: ChatRoomType
): Serializable {

    fun toChatRoom(createBy: Account): ChatRoom {
        return ChatRoom(null, createBy, password, title, LocalDateTime.now(), type)
    }
}
