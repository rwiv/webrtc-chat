package com.example.cloverchatserver.chat.controller

import com.example.cloverchatserver.board.controller.ResponseChatRoom
import com.example.cloverchatserver.user.controller.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseChatMessage(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val createUser: ResponseUser,
    val content: String,
    val createAt: LocalDateTime
) : Serializable
