package com.example.cloverchatserver.chat.message.controller.domain

import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseStompChatMessage(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val createUser: ResponseUser,
    val content: String,
    val createAt: LocalDateTime
) : Serializable
