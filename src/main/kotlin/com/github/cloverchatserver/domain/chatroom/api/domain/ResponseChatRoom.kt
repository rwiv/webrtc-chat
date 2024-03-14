package com.github.cloverchatserver.domain.chatroom.api.domain

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseChatRoom(
    val id: Long,
    val createUser: ResponseUser,
    val title: String,
    val createDate: LocalDateTime,
    val type: ChatRoomType
): Serializable
