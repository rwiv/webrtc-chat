package com.github.cloverchatserver.domain.chatuser.business

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUserRepository
import com.github.cloverchatserver.common.error.exception.NotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatUserService(
    val chatUserRepository: ChatUserRepository,
    val chatRoomService: ChatRoomService,
    val accountService: AccountService
) {

    fun findAll(): MutableList<ChatUser> {
        return chatUserRepository.findAll()
    }

    fun findByChatRoomIdAssertUser(chatRoomId: Long, accountResponse: AccountResponse): List<ChatUser> {
        val chatUsers = findByChatRoomId(chatRoomId)

        chatUsers
            .find { chatUser -> chatUser.account.id == accountResponse.id }
            ?: throw AccessDeniedException("You are not a chat room member")

        return chatUsers
    }

    fun findByChatRoomId(chatRoomId: Long): List<ChatUser> {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        return chatUserRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    fun createChatUser(chatRoomId: Long, accountResponse: AccountResponse, sessionId: String): ChatUser {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        val user = accountService.findById(accountResponse.id)
            ?: throw NotFoundException("not found account")

        val chatUsers = chatUserRepository.findByChatRoomAndAccount(chatRoom, user)
        if (chatUsers.isNotEmpty()) {
            throw DuplicatedChatUserException("ChatUser is already exist")
        }

        val newChatUser = ChatUser(null, chatRoom, user, sessionId)
        chatRoom.chatUsers.add(newChatUser)

        return chatUserRepository.save(newChatUser)
    }

    @Transactional
    fun deleteChatUserBySessionId(sessionId: String, accountResponse: AccountResponse): ChatUser {
        val chatUser = chatUserRepository.findBySessionId(sessionId)
            ?: throw NotFoundException("not found chatuser")

        if (chatUser.account.id != accountResponse.id) {
            throw AccessDeniedException("You are not a chat room member")
        }

        chatUserRepository.delete(chatUser)

        return chatUser
    }

//    @Transactional
//    fun deleteChatUserByUserId(responseUser: ResponseUser): List<ChatUser> {
//        val user = userService.getUserBy(responseUser.id)
//            ?: throw UserNotFoundException()
//
//        val chatUsers = chatUserRepository.findByUser(user)
//
//        chatUserRepository.deleteAll(chatUsers)
//
//        return chatUsers
//    }
//
//    @Transactional
//    fun deleteChatUserByUserIdAndChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser> {
//        val chatRoom = chatRoomService.getChatRoomById(chatRoomId)
//            ?: throw ChatRoomNotFoundException()
//
//        val user = userService.getUserBy(responseUser.id)
//            ?: throw UserNotFoundException()
//
//        val chatUsers = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
//
//        chatUserRepository.deleteAll(chatUsers)
//
//        return chatUsers
//    }
}