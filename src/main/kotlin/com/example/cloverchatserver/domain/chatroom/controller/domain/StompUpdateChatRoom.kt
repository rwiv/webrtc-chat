package com.example.cloverchatserver.domain.chatroom.controller.domain

import com.example.cloverchatserver.common.MethodType

data class StompUpdateChatRoom(
    val type: MethodType,
    val chatRoom: ResponseChatRoom
)