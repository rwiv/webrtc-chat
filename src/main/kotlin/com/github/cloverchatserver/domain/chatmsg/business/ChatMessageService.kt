package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.common.error.exception.NotFoundException
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageReadEvent
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val accountRepository: AccountRepository,
    private val publisher: ApplicationEventPublisher,
) {

    fun findAll(): MutableList<ChatMessage> {
        return chatMessageRepository.findAll()
    }

    fun findByChatRoomId(chatRoomId: Long): List<ChatMessage> {
        val chatRoom = chatRoomRepository.findById(chatRoomId).getOrNull()
            ?: throw NotFoundException("not found chatroom")

        val chatMessages = chatMessageRepository.findByChatRoom(chatRoom)

        publisher.publishEvent(ChatMessageReadEvent("read"))

        return chatMessages
    }

    @Transactional
    fun create(creation: ChatMessageCreation, accountResponse: AccountResponse): ChatMessage {
        val chatRoom = chatRoomRepository.findById(creation.chatRoomId).getOrNull()
            ?: throw NotFoundException("not found chatroom")

        val createUser = accountRepository.findById(accountResponse.id).getOrNull()
            ?: throw NotFoundException("not found account")

        val chatMessage = creation.toChatMessage(chatRoom, createUser)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}