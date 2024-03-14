package com.github.cloverchatserver.domain.chatroom.service

import com.github.cloverchatserver.domain.chatroom.controller.domain.RequestChatRoomCreateForm
import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser

interface ChatRoomService {

    fun getChatRoomById(chatRoomId: Long): ChatRoom?
    fun getChatRoomList(): List<ChatRoom>

    fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom
}