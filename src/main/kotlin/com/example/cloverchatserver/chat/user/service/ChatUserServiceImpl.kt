package com.example.cloverchatserver.chat.user.service

import com.example.cloverchatserver.board.service.ChatRoomNotFoundException
import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.chat.user.repository.ChatUser
import com.example.cloverchatserver.chat.user.repository.ChatUserRepository
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import com.example.cloverchatserver.user.service.UserNotFoundException
import com.example.cloverchatserver.user.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatUserServiceImpl(
    val chatUserRepository: ChatUserRepository,
    val chatRoomService: ChatRoomService,
    val userService: UserService
) : ChatUserService {

    @Transactional
    override fun getChatUsersByChatRoomIdAssertUser(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser> {
        val chatUsers = getChatUsersByChatRoomId(chatRoomId, responseUser)

        chatUsers.find { chatUser -> chatUser.user.id == responseUser.id }
            ?: throw AccessDeniedException("You are not a chat room member")

        return chatUsers
    }

    @Transactional
    override fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser> {
        val chatRoom = chatRoomService.getChatRoomById(chatRoomId)
            ?: throw ChatRoomNotFoundException()

        return chatUserRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    override fun createChatUser(chatRoomId: Long, responseUser: ResponseUser, sessionId: String): ChatUser {
        val chatRoom = chatRoomService.getChatRoomById(chatRoomId)
            ?: throw ChatRoomNotFoundException()

        val user = userService.getUserBy(responseUser.id)
            ?: throw UserNotFoundException()

        val chatUsers = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
        if (chatUsers.isNotEmpty()) {
            throw DuplicatedChatUserException("ChatUser is already exist")
        }

        val newChatUser = ChatUser(null, chatRoom, user, sessionId)
        chatRoom.chatUsers.add(newChatUser)

        return chatUserRepository.save(newChatUser)
    }

    @Transactional
    override fun deleteChatUserBySessionId(sessionId: String, responseUser: ResponseUser): ChatUser {
        val chatUser = chatUserRepository.findBySessionId(sessionId)
            ?: throw NotFoundChatUserException("Not Found Chat User")

        if (chatUser.user.id != responseUser.id) {
            throw AccessDeniedException("You are not a chat room member")
        }

        chatUserRepository.delete(chatUser)

        return chatUser
    }

//    @Transactional
//    override fun deleteChatUserByUserId(responseUser: ResponseUser): List<ChatUser> {
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
//    override fun deleteChatUserByUserIdAndChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ChatUser> {
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