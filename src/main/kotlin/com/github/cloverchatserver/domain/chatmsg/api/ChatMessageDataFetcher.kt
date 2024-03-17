package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import java.time.OffsetDateTime
import java.time.ZoneOffset

@DgsComponent
class ChatMessageDataFetcher(
    private val chatMessageService: ChatMessageService,
) {

    @DgsQuery
    fun chatMessagesAll(): MutableList<ChatMessage> {
        return chatMessageService.findAll()
    }

    @DgsData(parentType = "ChatMessage")
    fun createAt(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatMessage>()
        return chatRoom.createAt.atOffset(ZoneOffset.UTC)
    }
}
