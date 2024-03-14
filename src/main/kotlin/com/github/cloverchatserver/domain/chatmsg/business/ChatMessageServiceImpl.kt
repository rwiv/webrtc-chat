package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.domain.chatroom.business.ChatRoomNotFoundException
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestChatMessagesReadForm
import com.github.cloverchatserver.domain.chatmsg.api.domain.RequestStompChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import com.github.cloverchatserver.domain.account.business.AccountNotFoundException
import com.github.cloverchatserver.domain.account.business.AccountService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageServiceImpl(

    val chatMessageRepository: ChatMessageRepository,
    val chatRoomService: ChatRoomService,
    val accountService: AccountService

) : ChatMessageService {

    @Transactional
    override fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage> {
        val chatRoom = chatRoomService.getChatRoomById(form.chatRoomId)
            ?: throw ChatRoomNotFoundException()

        if (form.password != chatRoom.password) {
            throw BadCredentialsException("password is wrong")
        }

        return chatMessageRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    override fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage {
        val chatRoom = chatRoomService.getChatRoomById(requestStompChatMessage.chatRoomId)
            ?: throw ChatRoomNotFoundException()

        val createUser = accountService.getUserBy(responseUser.id)
            ?: throw AccountNotFoundException()

        val chatMessage = requestStompChatMessage.toChatMessage(chatRoom, createUser)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}