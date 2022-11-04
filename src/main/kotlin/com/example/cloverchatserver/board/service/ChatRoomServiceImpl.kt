package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.domain.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import com.example.cloverchatserver.user.service.UserService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

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
    override fun deleteChatRoom(chatRoomId: Long, password: String) {
        val chatRoom = chatRoomRepository
            .findById(chatRoomId)
            .orElseThrow { throw ChatRoomNotFoundException() }

        if (chatRoom.password != password) {
            throw BadCredentialsException("비밀번호가 다릅니다")
        }

        chatRoomRepository.delete(chatRoom)
    }
}