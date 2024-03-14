package com.github.cloverchatserver.domain.chatmsg.controller.domain

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoomType
import java.io.Serializable

data class RequestChatMessagesReadForm(
    val chatRoomId: Long,
    val type: ChatRoomType,
    val password: String?
): Serializable