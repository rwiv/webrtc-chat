package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import com.github.cloverchatserver.domain.friend.business.FriendService
import com.github.cloverchatserver.domain.friend.persistence.Friend
import com.netflix.graphql.dgs.*
import org.springframework.security.core.Authentication

@DgsComponent
class AccountDataFetcher(
    private val accountService: AccountService,
    private val chatRoomService: ChatRoomService,
    private val chatUserService: ChatUserService,
    private val friendService: FriendService,
) {

    @DgsQuery
    fun accountsAll(): List<Account> {
        val accounts = accountService.findAll()
        return accounts
    }

    @DgsQuery
    fun account(@InputArgument username: String?, authentication: Authentication): Account? {
        if (username !== null) {
            return accountService.findByUsername(username)
        } else {
            val accountResponse = authentication.details as AccountResponse
            return accountService.findById(accountResponse.id)
        }
    }

    @DgsQuery
    fun accounts(@InputArgument id: Long): Account? {
        return accountService.findById(id)
    }

    @DgsMutation
    fun createAccount(creation: AccountCreation): Account {
        return accountService.create(creation)
    }

    @DgsData(parentType = "Account")
    fun chatUsers(dfe: DgsDataFetchingEnvironment): List<ChatUser> {
        val account = dfe.getSource<Account>()
        return chatUserService.findByAccount(account)
    }

    @DgsData(parentType = "Account")
    fun chatRooms(dfe: DgsDataFetchingEnvironment): List<ChatRoom> {
        val account = dfe.getSource<Account>()
        return chatRoomService.findByAccount(account)
    }

    @DgsData(parentType = "Account")
    fun friends(dfe: DgsDataFetchingEnvironment): List<Friend> {
        val account = dfe.getSource<Account>()
        return friendService.findByAccount(account)
    }
}
