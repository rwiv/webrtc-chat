package com.github.cloverchatserver.domain.chatmsg.api.domain

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import java.io.Serializable

data class RequestChatMessagesReadForm(
    val chatRoomId: Long,
    val type: ChatRoomType,
    val password: String?
): Serializable