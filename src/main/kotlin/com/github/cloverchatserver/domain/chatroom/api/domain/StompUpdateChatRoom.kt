package com.github.cloverchatserver.domain.chatroom.api.domain

import com.github.cloverchatserver.common.MethodType

data class StompUpdateChatRoom(
    val type: MethodType,
    val chatRoom: ResponseChatRoom
)