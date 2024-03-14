package com.github.cloverchatserver.domain.chatroom.controller.domain

import com.github.cloverchatserver.common.MethodType

data class StompUpdateChatRoom(
    val type: MethodType,
    val chatRoom: ResponseChatRoom
)