package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.user.controller.ResponseUser
import java.io.Serializable
import java.time.LocalDateTime

data class ResponseChatRoom(
    val id: Long,
    val createBy: ResponseUser,
    val title: String,
    val createDate: LocalDateTime
): Serializable
