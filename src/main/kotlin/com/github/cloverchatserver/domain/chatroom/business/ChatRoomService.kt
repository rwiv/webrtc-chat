package com.github.cloverchatserver.domain.chatroom.business

import com.github.cloverchatserver.common.error.exception.HttpException
import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomRepository
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.common.error.exception.NotFoundException
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUserRepository
import com.github.cloverchatserver.domain.friend.persistence.Friend
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ChatRoomService(
    private val accountRepository: AccountRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatUserRepository: ChatUserRepository,
    private val chatUserService: ChatUserService,
) {

    fun findById(chatRoomId: Long): ChatRoom? {
        return chatRoomRepository.findById(chatRoomId).getOrNull()
    }

    fun findAll(): List<ChatRoom> = chatRoomRepository.findAll()

    fun findByAccount(account: Account): List<ChatRoom> {
        return chatUserRepository.findByAccount(account).map { it.chatRoom }
    }

    @Transactional
    fun create(creation: ChatRoomCreation, createdById: Long): ChatRoom {
        val createdBy = accountRepository.findById(createdById).getOrNull()
            ?: throw NotFoundException("not found account")
        val chatRoom = chatRoomRepository.save(creation.toTbc(createdBy))

        chatUserService.create(chatRoom.id!!, chatRoom.password, createdById)

        return chatRoom
    }

    @Transactional
    fun createByFriend(creation: ChatRoomCreation, friend: Friend, me: AccountResponse): ChatRoom {
        if (friend.from.id != me.id) {
            throw HttpException(400, "friend id is invalid")
        }
        val chatRoom = create(creation, me.id)

        chatUserService.create(chatRoom.id!!, chatRoom.password, friend.to.id!!)

        return chatRoom
    }

    @Transactional
    fun delete(chatRoomId: Long, accountResponse: AccountResponse): ChatRoom {
        val chatRoom = findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")
        if (accountResponse.id != chatRoom.createdBy.id) {
            throw AccessDeniedException("This user is not ChatRoom CreateUser")
        }

        chatRoomRepository.delete(chatRoom)
        return chatRoom
    }
}
