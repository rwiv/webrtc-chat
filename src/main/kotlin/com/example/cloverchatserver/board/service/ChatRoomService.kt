package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatRoomService {

    fun getChatRoomById(chatRoomId: Long): ChatRoom?
    fun getChatRoomList(): List<ChatRoom>

    fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom
}