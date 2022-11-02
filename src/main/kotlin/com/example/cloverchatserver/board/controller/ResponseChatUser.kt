package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.user.controller.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
