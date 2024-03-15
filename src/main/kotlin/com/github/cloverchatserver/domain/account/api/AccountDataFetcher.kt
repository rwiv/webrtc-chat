package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.persistence.Account
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class AccountDataFetcher(
    private val accountService: AccountService,
) {

    @DgsQuery
    fun accounts(): List<Account> {
        val accounts = accountService.findAll()
        return accounts
    }
}