package com.example.cloverchatserver.chat.user.service

import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.chat.user.controller.domain.ResponseChatUser
import com.example.cloverchatserver.chat.user.repository.ChatUser
import com.example.cloverchatserver.chat.user.repository.ChatUserRepository
import com.example.cloverchatserver.user.controller.domain.ResponseUser
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
    override fun getChatUsersByChatRoomId(chatRoomId: Long, responseUser: ResponseUser): List<ResponseChatUser> {
        val chatRoom = chatRoomService.getChatRoomBy(chatRoomId)

        val chatUsers = chatUserRepository.findByChatRoom(chatRoom)
            .map { c -> c.toResponseChatUser() }

        chatUsers.find { chatUser -> chatUser.user.id == responseUser.id } ?: throw AccessDeniedException("You are not a chat room member")

        return chatUsers
    }

    @Transactional
    override fun createChatUser(chatRoomId: Long, responseUser: ResponseUser): ResponseChatUser {
        val chatRoom = chatRoomService.getChatRoomBy(chatRoomId)
        val user = userService.getUserBy(responseUser.id)

        val chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
        if (chatUser != null) {
            throw DuplicatedChatUserException("ChatUser is already exist")
        }

        val newChatUser = ChatUser(null, chatRoom, user)

        return chatUserRepository.save(newChatUser)
            .toResponseChatUser()
    }

    @Transactional
    override fun deleteChatUser(chatRoomId: Long, responseUser: ResponseUser): ResponseChatUser {
        val chatRoom = chatRoomService.getChatRoomBy(chatRoomId)
        val user = userService.getUserBy(responseUser.id)

        val chatUser: ChatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
            ?: throw NotFoundChatUserException("ChatUser is Not Found")

        chatUserRepository.delete(chatUser)

        return chatUser.toResponseChatUser()
    }
}