package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserCreation
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.core.Authentication

@DgsComponent
class ChatUserDataFetcher(
    private val chatUserService: ChatUserService,
) {

    @DgsQuery
    fun chatUsersAll(): MutableList<ChatUser> {
        return chatUserService.findAll()
    }

    @DgsMutation
    fun createChatUser(
        @InputArgument chatRoomId: Long,
        @InputArgument password: String? = null,
        authentication: Authentication,
    ): ChatUser {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.create(ChatUserCreation(chatRoomId, password, accountResponse.id))
    }

    @DgsMutation
    fun deleteChatUserMe(
        @InputArgument chatRoomId: Long,
        authentication: Authentication,
    ): ChatUser {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.deleteByAccountId(chatRoomId, accountResponse.id)
    }
}
