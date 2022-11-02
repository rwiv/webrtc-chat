package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom

interface ChatRoomService {

    fun getChatRoomList(): List<ResponseChatRoom>

    fun createChatRoom(chatRoomCreateForm: ChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, password: String)
}