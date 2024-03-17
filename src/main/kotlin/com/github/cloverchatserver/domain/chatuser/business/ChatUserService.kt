package com.github.cloverchatserver.domain.chatuser.business

import com.github.cloverchatserver.common.error.exception.HttpException
import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUserRepository
import com.github.cloverchatserver.common.error.exception.NotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatUserService(
    val chatUserRepository: ChatUserRepository,
    val chatRoomService: ChatRoomService,
    val accountService: AccountService
) {

    fun findAll(): MutableList<ChatUser> {
        return chatUserRepository.findAll()
    }

    fun findByChatRoomIdAssertUser(chatRoomId: Long, accountResponse: AccountResponse): List<ChatUser> {
        val chatUsers = findByChatRoomId(chatRoomId)

        chatUsers
            .find { chatUser -> chatUser.account.id == accountResponse.id }
            ?: throw AccessDeniedException("You are not a chat room member")

        return chatUsers
    }

    fun findByChatRoomId(chatRoomId: Long): List<ChatUser> {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")

        return chatUserRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    fun createChatUser(chatRoomId: Long, password: String?, accountResponse: AccountResponse): ChatUser {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatroom")
        if (chatRoom.password !== password) {
            throw HttpException(403, "invalid password")
        }

        val account = accountService.findById(accountResponse.id)
            ?: throw NotFoundException("not found account")

        val chatUsers = chatUserRepository.findByChatRoomAndAccount(chatRoom, account)
        if (chatUsers.isNotEmpty()) {
            throw DuplicatedChatUserException("ChatUser is already exist")
        }

        val newChatUser = ChatUser(null, chatRoom, account)
        chatRoom.chatUsers.add(newChatUser)

        return chatUserRepository.save(newChatUser)
    }

    @Transactional
    fun deleteChatUserByAccountId(chatRoomId: Long, accountId: Long): ChatUser {
        val chatRoom = chatRoomService.findById(chatRoomId)
            ?: throw NotFoundException("not found chatRoom")
        val account = accountService.findById(accountId)
            ?: throw NotFoundException("not found account")

        val chatUsers = chatUserRepository.findByChatRoomAndAccount(chatRoom, account)
        if (chatUsers.size > 1) {
            throw HttpException(400, "duplicated chatUsers")
        }
        if (chatUsers.size === 0) {
            throw NotFoundException("not found chatUser")
        }

        chatUserRepository.delete(chatUsers[0])
        return chatUsers[0]
    }
}