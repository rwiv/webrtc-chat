package com.example.cloverchatserver.chat.message.service

import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.chat.message.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.chat.message.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.chat.message.repository.ChatMessage
import com.example.cloverchatserver.chat.message.repository.ChatMessageRepository
import com.example.cloverchatserver.user.service.UserService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageServiceImpl(

    val chatMessageRepository: ChatMessageRepository,
    val chatRoomService: ChatRoomService,
    val userService: UserService

) : ChatMessageService {

    @Transactional
    override fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage> {
        val chatRoom = chatRoomService.getChatRoomBy(form.chatRoomId)

        if (form.password != chatRoom.password) {
            throw BadCredentialsException("password is wrong")
        }

        return chatMessageRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    override fun createChatMessage(requestStompChatMessage: RequestStompChatMessage): ChatMessage {
        val chatRoom = chatRoomService.getChatRoomBy(requestStompChatMessage.chatRoomId)
        val createUser = userService.getUserBy(requestStompChatMessage.createUserId)

        return chatMessageRepository.save(requestStompChatMessage.toChatMessage(chatRoom, createUser))
    }
}