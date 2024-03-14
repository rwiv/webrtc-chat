package com.github.cloverchatserver.domain.chatuser.controller.domain

import com.github.cloverchatserver.common.MethodType

data class StompUpdateChatUser(
    val type: MethodType,
    val chatUser: ResponseChatUser
)