package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessagesFindForm
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class ChatMessageDataFetcher(
    private val chatMessageService: ChatMessageService,
) {

    @DgsQuery
    fun chatMessages(): MutableList<ChatMessage> {
        return chatMessageService.findAll()
    }

    @DgsQuery
    fun chatMessagesByForm(form: ChatMessagesFindForm): List<ChatMessage> {
        return chatMessageService.findByForm(form)
    }
}
