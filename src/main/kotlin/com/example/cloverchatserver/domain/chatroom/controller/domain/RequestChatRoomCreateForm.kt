package com.example.cloverchatserver.domain.chatroom.controller.domain

import com.example.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.example.cloverchatserver.domain.chatroom.repository.ChatRoomType
import com.example.cloverchatserver.domain.user.repository.User
import java.io.Serializable
import java.time.LocalDateTime

data class RequestChatRoomCreateForm(
    val createUserId: Long,
    val password: String?,
    val title: String,
    val type: ChatRoomType
): Serializable {

    fun toChatRoom(createBy: User): ChatRoom {
        return ChatRoom(null, createBy, password, title, LocalDateTime.now(), type)
    }
}
