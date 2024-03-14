package com.example.cloverchatserver.domain.chatroom.controller.domain

import com.example.cloverchatserver.domain.chatroom.repository.ChatRoomType
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseChatRoom(
    val id: Long,
    val createUser: ResponseUser,
    val title: String,
    val createDate: LocalDateTime,
    val type: ChatRoomType
): Serializable
