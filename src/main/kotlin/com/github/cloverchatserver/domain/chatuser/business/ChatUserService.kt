package com.github.cloverchatserver.domain.chatuser.business

import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser

interface ChatUserService {

    fun getChatUsersByChatRoomIdAssertUser(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>
    fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>

    fun createChatUser(chatRoomId: Long, responseUser: ResponseUser, sessionId: String): ChatUser

    fun deleteChatUserBySessionId(sessionId: String, responseUser: ResponseUser): ChatUser
//    fun deleteChatUserByUserId(responseUser: ResponseUser): List<ChatUser>
//    fun deleteChatUserByUserIdAndChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser>
}