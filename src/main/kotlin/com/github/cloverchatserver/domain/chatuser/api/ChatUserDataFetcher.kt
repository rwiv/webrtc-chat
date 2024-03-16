package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.core.Authentication

@DgsComponent
class ChatUserDataFetcher(
    private val chatUserService: ChatUserService,
) {

    @DgsQuery
    fun chatUsers(): MutableList<ChatUser> {
        return chatUserService.findAll()
    }

    @DgsQuery
    fun chatUsersByChatRoomId(@InputArgument chatRoomId: Long, authentication: Authentication): List<ChatUser> {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.findByChatRoomIdAssertUser(chatRoomId, accountResponse)
    }
}
