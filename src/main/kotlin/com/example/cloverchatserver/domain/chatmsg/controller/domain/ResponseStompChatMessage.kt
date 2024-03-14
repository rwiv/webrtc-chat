package com.example.cloverchatserver.domain.chatmsg.controller.domain

import com.example.cloverchatserver.domain.chatroom.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseStompChatMessage(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val createUser: ResponseUser,
    val content: String,
    val createAt: LocalDateTime
) : Serializable
