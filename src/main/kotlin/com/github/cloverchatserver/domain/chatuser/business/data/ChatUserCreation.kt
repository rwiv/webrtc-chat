package com.github.cloverchatserver.domain.chatuser.business.data

data class ChatUserCreation(
    val chatRoomId: Long,
    val chatRoomPassword: String?,
    val accountId: Long,
)
