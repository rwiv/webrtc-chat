package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.netflix.graphql.dgs.*
import org.springframework.context.ApplicationEventPublisher
import java.time.OffsetDateTime
import java.time.ZoneOffset

@DgsComponent
class ChatMessageDataFetcher(
    private val chatMessageService: ChatMessageService,
    private val publisher: ApplicationEventPublisher,
) {

    @DgsQuery
    fun chatMessagesAll(): MutableList<ChatMessage> {
        return chatMessageService.findAll()
    }

    @DgsQuery
    fun chatMessage(@InputArgument id: Long): ChatMessage? {
        return chatMessageService.findById(id)
    }

    @DgsQuery
    fun chatMessages(
        @InputArgument chatRoomId: Long,
        @InputArgument page: Int,
        @InputArgument size: Int,
        @InputArgument offset: Int,
    ): List<ChatMessage> {
        return chatMessageService.findByPage(chatRoomId, page, size, offset)
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
