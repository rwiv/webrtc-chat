package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.repository.ChatRoom
import java.io.Serializable
import java.time.LocalDateTime

data class RequestChatRoom(
    val createBy: String,
    val password: String,
    val title: String,
): Serializable {

    val createDate: LocalDateTime = LocalDateTime.now()

    fun toChatRoom() = ChatRoom(null, createBy, password, title, createDate)
}
