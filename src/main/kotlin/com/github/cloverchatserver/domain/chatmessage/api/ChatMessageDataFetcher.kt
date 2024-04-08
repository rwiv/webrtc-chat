package com.github.cloverchatserver.domain.chatmessage.api

import com.github.cloverchatserver.domain.chatmessage.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage
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

    @DgsData(parentType = "ChatMessage")
    fun createdAt(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatRoom = dfe.getSource<ChatMessage>()
        return chatRoom.createdAt.atOffset(ZoneOffset.UTC)
    }
}
