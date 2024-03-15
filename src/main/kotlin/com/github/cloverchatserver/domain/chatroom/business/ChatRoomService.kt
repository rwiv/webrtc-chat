package com.github.cloverchatserver.domain.chatroom.business

import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomRepository
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.common.error.exception.NotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ChatRoomService(
    val chatRoomRepository: ChatRoomRepository,
    val accountService: AccountService
) {

    fun findById(chatRoomId: Long): ChatRoom? {
        return chatRoomRepository.findById(chatRoomId).getOrNull()
    }

    fun findAll(): List<ChatRoom> = chatRoomRepository.findAll()

    @Transactional
    fun createChatRoom(creation: ChatRoomCreation): ChatRoom {
        val createBy = accountService.findById(creation.createUserId)
            ?: throw NotFoundException("not found account")
        val requestChatRoom = creation.toChatRoom(createBy)
        return chatRoomRepository.save(requestChatRoom)
    }

    @Transactional
    fun deleteChatRoom(chatRoomId: Long, accountResponse: AccountResponse): ChatRoom {
        val chatRoom = findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")
        if (accountResponse.id != chatRoom.createAccount.id) {
            throw AccessDeniedException("This user is not ChatRoom CreateUser")
        }

        chatRoomRepository.delete(chatRoom)
        return chatRoom
    }
}
