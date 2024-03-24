package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.account.persistence.Account
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

    // TODO: remove
    @DgsData(parentType = "ChatMessage")
    fun createAccount(dfe: DgsDataFetchingEnvironment): Account {
        val chatMessage = dfe.getSource<ChatMessage>()
        return chatMessage.createdBy
    }

    // TODO: edit field name to 'createdBy'
    @DgsData(parentType = "ChatMessage")
    fun createAt(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatMessage>()
        return chatRoom.createdAt.atOffset(ZoneOffset.UTC)
    }
}
