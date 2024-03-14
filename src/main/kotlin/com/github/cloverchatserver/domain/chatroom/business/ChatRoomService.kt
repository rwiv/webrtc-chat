package com.github.cloverchatserver.domain.chatroom.business

import com.github.cloverchatserver.domain.chatroom.api.domain.RequestChatRoomCreateForm
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser

interface ChatRoomService {

    fun getChatRoomById(chatRoomId: Long): ChatRoom?
    fun getChatRoomList(): List<ChatRoom>

    fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom
}