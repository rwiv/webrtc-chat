package com.example.cloverchatserver.domain.chatuser.controller.domain

import com.example.cloverchatserver.domain.chatroom.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
