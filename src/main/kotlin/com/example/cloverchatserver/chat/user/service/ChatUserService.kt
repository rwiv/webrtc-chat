package com.example.cloverchatserver.chat.user.service

import com.example.cloverchatserver.chat.user.repository.ChatUser
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatUserService {

    fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>

    fun createChatUser(chatRoomId: Long, responseUser: ResponseUser): ChatUser

    fun deleteChatUserByUserId(responseUser: ResponseUser): List<ChatUser>
    fun deleteChatUserByUserIdAndChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>
}