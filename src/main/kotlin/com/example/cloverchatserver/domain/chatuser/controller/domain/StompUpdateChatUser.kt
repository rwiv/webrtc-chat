package com.example.cloverchatserver.domain.chatuser.controller.domain

import com.example.cloverchatserver.common.MethodType

data class StompUpdateChatUser(
    val type: MethodType,
    val chatUser: ResponseChatUser
)