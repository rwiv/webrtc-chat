package com.github.cloverchatserver.domain.chatroom.api.data

import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType

data class ChatRoomCreateRequest(
    val password: String?,
    val title: String,
    val type: ChatRoomType
) {
    fun toChatRoomCreation(chatRoomId: Long) = ChatRoomCreation(
        chatRoomId,
        password,
        title,
        type,
    )
}
