package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.common.error.exception.NotFoundException
import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageReadEvent
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val chatRoomService: ChatRoomService,
    private val accountService: AccountService,
    private val publisher: ApplicationEventPublisher,
) {

    fun findAll(): MutableList<ChatMessage> {
        return chatMessageRepository.findAll()
    }

    fun findByChatRoomId(chatRoomId: Long): List<ChatMessage> {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        val chatMessages = chatMessageRepository.findByChatRoom(chatRoom)

        publisher.publishEvent(ChatMessageReadEvent("read"))

        return chatMessages
    }

    @Transactional
    fun createChatMessage(creation: ChatMessageCreation, accountResponse: AccountResponse): ChatMessage {
        val chatRoom = chatRoomService.findById(creation.chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        val createUser = accountService.findById(accountResponse.id)
            ?: throw NotFoundException("not found account")

        val chatMessage = creation.toChatMessage(chatRoom, createUser)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}