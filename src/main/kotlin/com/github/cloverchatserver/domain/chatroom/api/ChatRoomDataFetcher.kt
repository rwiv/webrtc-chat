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
    fun chatRooms(): List<ChatRoom> {
        return chatRoomService.findAll()
    }

    @DgsMutation
    fun createChatRoom(req: ChatRoomCreateRequest, authentication: Authentication): ChatRoom {
        val accountResponse = authentication.details as AccountResponse
        val creation = req.toChatRoomCreation(accountResponse.id)
        return chatRoomService.createChatRoom(creation)
    }

    @DgsMutation
    fun deleteChatRoom(@InputArgument chatRoomId: Long, authentication: Authentication): ChatRoom {
        val accountResponse = authentication.details as AccountResponse
        return chatRoomService.deleteChatRoom(chatRoomId, accountResponse)
    }

    @DgsData(parentType = "ChatRoom")
    fun createDate(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatRoom>()
        return chatRoom.createDate.atOffset(ZoneOffset.UTC)
    }
}
