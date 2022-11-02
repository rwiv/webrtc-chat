package com.example.cloverchatserver.board.service

import com.example.cloverchatserver.board.controller.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.ResponseChatRoom
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import com.example.cloverchatserver.user.service.UserService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ChatRoomServiceImpl(

    val chatRoomRepository: ChatRoomRepository,
    val userService: UserService

) : ChatRoomService {
    override fun getChatRoomBy(chatRoomId: Long): ChatRoom {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow { throw RuntimeException("Not Found ChatRoom") }
    }

    override fun getChatRoomList(): List<ResponseChatRoom> {
        return chatRoomRepository
            .findAll()
            .map { p -> p.toResponseChatRoom() }
    }

    override fun createChatRoom(chatRoomCreateForm: ChatRoomCreateForm): ChatRoom {
        val createBy = userService.getUserBy(chatRoomCreateForm.createUserId)
        val requestChatRoom = chatRoomCreateForm.toChatRoom(createBy)

        return chatRoomRepository.save(requestChatRoom)
    }

    override fun deleteChatRoom(chatRoomId: Long, password: String) {
        val chatRoom = chatRoomRepository
            .findById(chatRoomId)
            .orElseThrow { throw RuntimeException("ChatRoom Not Found!") }

        if (chatRoom.password != password) {
            throw RuntimeException("비밀번호가 다릅니다")
        }

        chatRoomRepository.delete(chatRoom)
    }
}