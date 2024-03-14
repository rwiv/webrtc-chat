package com.github.cloverchatserver.domain.chatmsg.business.data

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import java.io.Serializable

data class ChatMessagesFindForm(
    val chatRoomId: Long,
    val type: ChatRoomType,
    val password: String?
): Serializable