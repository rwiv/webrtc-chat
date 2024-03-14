package com.example.cloverchatserver.domain.chatroom.service

import com.example.cloverchatserver.domain.chatroom.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser

interface ChatRoomService {

    fun getChatRoomById(chatRoomId: Long): ChatRoom?
    fun getChatRoomList(): List<ChatRoom>

    fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom
}