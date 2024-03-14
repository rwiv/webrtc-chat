package com.github.cloverchatserver.domain.chatuser.controller.domain

import com.github.cloverchatserver.domain.chatroom.controller.domain.ResponseChatRoom
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
