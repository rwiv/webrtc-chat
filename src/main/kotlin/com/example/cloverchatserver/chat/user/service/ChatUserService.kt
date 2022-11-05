package com.example.cloverchatserver.chat.user.service

import com.example.cloverchatserver.chat.user.controller.domain.ResponseChatUser
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatUserService {

    fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ResponseChatUser>

    fun createChatUser(chatRoomId: Long, responseUser: ResponseUser): ResponseChatUser

    fun deleteChatUser(chatRoomId: Long, responseUser: ResponseUser): ResponseChatUser
}