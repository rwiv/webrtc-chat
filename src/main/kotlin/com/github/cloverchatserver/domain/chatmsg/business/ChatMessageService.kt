package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessagesFindForm
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.common.error.exception.NotFoundException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageService(
    val chatMessageRepository: ChatMessageRepository,
    val chatRoomService: ChatRoomService,
    val accountService: AccountService
) {

    @Transactional
    fun getChatMessagesBy(form: ChatMessagesFindForm): List<ChatMessage> {
        val chatRoom = chatRoomService.getChatRoomById(form.chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        if (form.password != chatRoom.password) {
            throw BadCredentialsException("password is wrong")
        }

        return chatMessageRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    fun createChatMessage(creation: ChatMessageCreation, accountResponse: AccountResponse): ChatMessage {
        val chatRoom = chatRoomService.getChatRoomById(creation.chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        val createUser = accountService.findById(accountResponse.id)
            ?: throw NotFoundException("not found account")

        val chatMessage = creation.toChatMessage(chatRoom, createUser)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}