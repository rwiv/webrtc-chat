package com.github.cloverchatserver.domain.chatuser.api.domain

import com.github.cloverchatserver.domain.chatroom.api.domain.ResponseChatRoom
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser

data class ResponseChatUser(
    val id: Long,
    val chatRoom: ResponseChatRoom,
    val user: ResponseUser
)
