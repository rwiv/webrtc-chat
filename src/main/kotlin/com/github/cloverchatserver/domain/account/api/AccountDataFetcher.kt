package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.persistence.Account
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication

@DgsComponent
class AccountDataFetcher(
    private val accountService: AccountService,
) {

    @DgsQuery
    fun accounts(): List<Account> {
        val accounts = accountService.findAll()
        return accounts
    }

    @DgsQuery
    fun me(authentication: Authentication): Account? {
        if (authentication is AnonymousAuthenticationToken) {
            return null
        }
        val accountResponse = authentication.details as AccountResponse
        return accountService.findById(accountResponse.id)
    }

    @DgsMutation
    fun createAccount(creation: AccountCreation): Account {
        return accountService.create(creation)
    }
}
