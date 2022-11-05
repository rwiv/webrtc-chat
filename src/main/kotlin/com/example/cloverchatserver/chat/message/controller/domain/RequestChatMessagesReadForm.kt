package com.example.cloverchatserver.chat.message.controller.domain

import com.example.cloverchatserver.board.repository.ChatRoomType
import java.io.Serializable

data class RequestChatMessagesReadForm(
    val chatRoomId: Long,
    val type: ChatRoomType,
    val password: String?
): Serializable