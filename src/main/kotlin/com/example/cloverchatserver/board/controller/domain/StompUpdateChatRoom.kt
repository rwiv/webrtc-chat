package com.example.cloverchatserver.board.controller.domain

import com.example.cloverchatserver.common.MethodType

data class StompUpdateChatRoom(
    val type: MethodType,
    val chatRoom: ResponseChatRoom
)