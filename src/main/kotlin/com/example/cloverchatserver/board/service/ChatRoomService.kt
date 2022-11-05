package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatRoomService {

    fun getChatRoomBy(chatRoomId: Long): ChatRoom
    fun getChatRoomList(): List<ResponseChatRoom>

    fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom

    fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser)
}