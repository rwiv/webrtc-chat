package com.github.cloverchatserver.domain.chatroom.api

import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
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

    @DgsData(parentType = "ChatRoom")
    fun createDate(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatRoom>()
        return chatRoom.createDate.atOffset(ZoneOffset.UTC)
    }
}
