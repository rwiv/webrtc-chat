package com.github.cloverchatserver.domain.chatroom.business

import com.github.cloverchatserver.domain.chatroom.api.domain.RequestChatRoomCreateForm
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomRepository
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import com.github.cloverchatserver.domain.account.business.AccountNotFoundException
import com.github.cloverchatserver.domain.account.business.AccountService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomServiceImpl(

    val chatRoomRepository: ChatRoomRepository,
    val accountService: AccountService

) : ChatRoomService {

    @Transactional
    override fun getChatRoomById(chatRoomId: Long): ChatRoom? {
        return chatRoomRepository.findById(chatRoomId)
            .orElseGet { null }
    }

    @Transactional
    override fun getChatRoomList(): List<ChatRoom> = chatRoomRepository.findAll()

    @Transactional
    override fun createChatRoom(requestChatRoomCreateForm: RequestChatRoomCreateForm): ChatRoom {
        val createBy = accountService.getUserBy(requestChatRoomCreateForm.createUserId)
            ?: throw AccountNotFoundException()

        val requestChatRoom = requestChatRoomCreateForm.toChatRoom(createBy)

        return chatRoomRepository.save(requestChatRoom)
    }

    @Transactional
    override fun deleteChatRoom(chatRoomId: Long, responseUser: ResponseUser): ChatRoom {
        val chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow { throw ChatRoomNotFoundException() }

        if (responseUser.id != chatRoom.createAccount.id) {
            throw AccessDeniedException("This user is not ChatRoom CreateUser")
        }

        chatRoomRepository.delete(chatRoom)

        return chatRoom
    }
}