package com.example.cloverchatserver.chat.user.controller.domain

import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.user.controller.domain.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
