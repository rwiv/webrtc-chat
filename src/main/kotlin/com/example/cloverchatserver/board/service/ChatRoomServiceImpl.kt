package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import com.example.cloverchatserver.user.service.UserNotFoundException
import com.example.cloverchatserver.user.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomServiceImpl(

    val chatRoomRepository: ChatRoomRepository,
    val userService: UserService

) : ChatRoomService {

    @Transactional
    override fun getChatRoomById(chatRoomId: Long): ChatRoom? {
        return chatRoomRepository.findById(chatRoomId)
            .orElseGet { null }
    }

    @Transactional
    override fun getChatRoomList(): List<ChatRoom> = chatRoomRepository.findAll()

    @Transactional
    override fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom {
        val createBy = userService.getUserBy(requestChatRoomCreateForm.createUserId)
            ?: throw UserNotFoundException()

        val requestChatRoom = requestChatRoomCreateForm.toChatRoom(createBy)

        return chatRoomRepository.save(requestChatRoom)
    }

    @Transactional
    override fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom {
        val chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow { throw ChatRoomNotFoundException() }

        if (responseUser.id != chatRoom.createUser.id) {
            throw AccessDeniedException("This user is not ChatRoom CreateUser")
        }

        chatRoomRepository.delete(chatRoom)

        return chatRoom
    }
}