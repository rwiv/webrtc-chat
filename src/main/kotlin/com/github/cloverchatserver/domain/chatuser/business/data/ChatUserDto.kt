package com.github.cloverchatserver.domain.chatuser.business.data

import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomDto
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser

data class ChatUserDto(
    val id: Long,
    val chatRoom: ChatRoomDto,
    val user: AccountResponse
) {
    companion object {
        fun of(chatUser: ChatUser) = ChatUserDto(
            chatUser.id!!,
            ChatRoomDto.of(chatUser.chatRoom),
            AccountResponse.of(chatUser.account),
        )
    }
}
