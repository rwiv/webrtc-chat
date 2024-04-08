package com.github.cloverchatserver.domain.chatmessage.business

import com.github.cloverchatserver.common.error.exception.NotFoundException
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import com.github.cloverchatserver.domain.chatmessage.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val accountRepository: AccountRepository,
) {

    fun findAll(): MutableList<ChatMessage> {
        return chatMessageRepository.findAll()
    }

    fun findById(id: Long): ChatMessage? {
        return chatMessageRepository.findById(id).getOrNull()
    }

    fun findByChatRoomId(chatRoomId: Long): List<ChatMessage> {
        val chatRoom = chatRoomRepository.findById(chatRoomId).getOrNull()
            ?: throw NotFoundException("not found chatroom")

        return chatMessageRepository.findByChatRoom(chatRoom)
    }

    fun findByPage(chatRoomId: Long, page: Int, size: Int, offset: Int): List<ChatMessage> {
        val chatRoom = chatRoomRepository.findById(chatRoomId).getOrNull()
            ?: throw NotFoundException("not found chatroom")
        return chatMessageRepository.findByDescOffset(chatRoom, page - 1, size, offset)
    }

    @Transactional
    fun create(creation: ChatMessageCreation): ChatMessage {
        val chatRoom = chatRoomRepository.findById(creation.chatRoomId).getOrNull()
            ?: throw NotFoundException("not found chatroom")

        val createUser = accountRepository.findById(creation.createUserId).getOrNull()
            ?: throw NotFoundException("not found account")

        val latest = chatMessageRepository.findLatestOne(chatRoom)
        val num = if (latest == null) 0 else latest.num + 1

        val chatMessage = creation.toChatMessage(chatRoom, createUser, num)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}