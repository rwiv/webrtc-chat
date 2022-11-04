package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import com.example.cloverchatserver.user.controller.domain.ResponseUser
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
    override fun getChatRoomBy(chatRoomId: Long): ChatRoom {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow { throw ChatRoomNotFoundException() }
    }

    @Transactional
    override fun getChatRoomList(): List<ResponseChatRoom> {
        return chatRoomRepository
            .findAll()
            .map { p -> p.toResponseChatRoom() }
    }

    @Transactional
    override fun createChatRoom(chatRoomCreateForm: ChatRoomCreateForm): ChatRoom {
        val createBy = userService.getUserBy(chatRoomCreateForm.createUserId)
        val requestChatRoom = chatRoomCreateForm.toChatRoom(createBy)

        return chatRoomRepository.save(requestChatRoom)
    }

    @Transactional
    override fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser) {
        val chatRoom = chatRoomRepository
            .findById(chatRoomId)
            .orElseThrow { throw ChatRoomNotFoundException() }

        if (responseUser.id != chatRoom.createUser.id) {
            throw AccessDeniedException("This user is not ChatRoom CreateUser")
        }

        chatRoomRepository.delete(chatRoom)
    }
}