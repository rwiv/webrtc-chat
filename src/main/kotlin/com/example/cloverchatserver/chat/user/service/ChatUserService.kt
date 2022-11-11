package com.example.cloverchatserver.chat.user.service

import com.example.cloverchatserver.chat.user.repository.ChatUser
import com.example.cloverchatserver.user.controller.domain.ResponseUser

interface ChatUserService {

    fun getChatUsersByChatRoomIdAssertUser(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>
    fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>

    fun createChatUser(chatRoomId: Long, responseUser: ResponseUser, sessionId: String): ChatUser

    fun deleteChatUserBySessionId(sessionId: String, responseUser: ResponseUser): ChatUser
//    fun deleteChatUserByUserId(responseUser: ResponseUser): List<ChatUser>
//    fun deleteChatUserByUserIdAndChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>
}