package com.github.cloverchatserver.domain.chatmsg.api.domain

import com.github.cloverchatserver.domain.chatroom.api.domain.ResponseChatRoom
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseStompChatMessage(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val createUser: ResponseUser,
    val content: String,
    val createAt: LocalDateTime
) : Serializable
