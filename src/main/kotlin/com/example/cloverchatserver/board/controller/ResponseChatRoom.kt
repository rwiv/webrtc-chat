package com.example.cloverchatserver.board.controller

import java.io.Serializable
import java.time.LocalDateTime

data class ResponseChatRoom(
    val id: Long,
    val createBy: String,
    val title: String,
    val createDate: LocalDateTime
): Serializable
