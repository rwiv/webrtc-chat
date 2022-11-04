package com.example.cloverchatserver.board.controller.domain

import com.example.cloverchatserver.user.controller.domain.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
