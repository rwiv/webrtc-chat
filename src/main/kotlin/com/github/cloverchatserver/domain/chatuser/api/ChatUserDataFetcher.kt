package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class ChatUserDataFetcher(
    private val chatUserService: ChatUserService,
) {

    @DgsQuery
    fun chatUsersAll(): MutableList<ChatUser> {
        return chatUserService.findAll()
    }
}
