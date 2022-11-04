package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom

interface ChatRoomService {

    fun getChatRoomBy(chatRoomId: Long): ChatRoom
    fun getChatRoomList(): List<ResponseChatRoom>

    fun createChatRoom(chatRoomCreateForm: ChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, password: String)
}