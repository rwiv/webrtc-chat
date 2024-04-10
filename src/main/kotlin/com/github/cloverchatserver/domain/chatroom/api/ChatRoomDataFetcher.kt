package com.github.cloverchatserver.domain.chatroom.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatroom.api.data.ChatRoomCreateRequest
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.netflix.graphql.dgs.*
import org.springframework.security.core.Authentication
import java.time.OffsetDateTime
import java.time.ZoneOffset

@DgsComponent
class ChatRoomDataFetcher(
    private val chatRoomService: ChatRoomService,
) {

    @DgsQuery
    fun chatRoom(@InputArgument id: Long): ChatRoom? {
        return chatRoomService.findById(id)
    }

    @DgsQuery
    fun chatRoomsAll(): List<ChatRoom> {
        return chatRoomService.findAll()
    }

    @DgsQuery
    fun chatRooms(@InputArgument page: Int, @InputArgument size: Int): List<ChatRoom> {
        return chatRoomService.findByPage(page, size)
    }

    @DgsMutation
    fun createChatRoom(req: ChatRoomCreateRequest, authentication: Authentication): ChatRoom {
        val accountResponse = authentication.details as AccountResponse
        return chatRoomService.create(req.toChatRoomCreation(accountResponse.id))
    }

    @DgsMutation
    fun createChatRoomByFriend(
        @InputArgument friendId: Long,
        authentication: Authentication,
    ): ChatRoom {
        val accountResponse = authentication.details as AccountResponse
        return chatRoomService.createByFriend(friendId, accountResponse)
    }

    @DgsMutation
    fun deleteChatRoom(@InputArgument chatRoomId: Long, authentication: Authentication): ChatRoom {
        val accountResponse = authentication.details as AccountResponse
        return chatRoomService.delete(chatRoomId, accountResponse)
    }

    @DgsData(parentType = "ChatRoom")
    fun createdAt(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatRoom>()
        return chatRoom.createdAt.atOffset(ZoneOffset.UTC)
    }
}
