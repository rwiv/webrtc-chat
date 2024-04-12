package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserCreation
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.netflix.graphql.dgs.*
import org.springframework.security.core.Authentication
import java.time.OffsetDateTime
import java.time.ZoneOffset

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
        @InputArgument password: String?,
        authentication: Authentication,
    ): ChatUser {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.create(ChatUserCreation(chatRoomId, password, accountResponse.id))
    }

    @DgsMutation
    fun createChatUserFromParticipant(
        @InputArgument chatRoomId: Long,
        @InputArgument accountId: Long,
        authentication: Authentication,
    ): ChatUser {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.createFromParticipant(chatRoomId, accountId, accountResponse)
    }

    @DgsMutation
    fun deleteChatUserMe(
        @InputArgument chatRoomId: Long,
        authentication: Authentication,
    ): ChatUser {
        val accountResponse = authentication.details as AccountResponse
        return chatUserService.deleteByAccountId(chatRoomId, accountResponse.id)
    }

    @DgsData(parentType = "ChatUser")
    fun createdAt(dfe: DgsDataFetchingEnvironment): OffsetDateTime {
        val chatUser = dfe.getSource<ChatUser>()
        return chatUser.createdAt.atOffset(ZoneOffset.UTC)
    }
}
