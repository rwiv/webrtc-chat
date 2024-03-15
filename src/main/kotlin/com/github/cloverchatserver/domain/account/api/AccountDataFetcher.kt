package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.persistence.Account
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import graphql.scalars.ExtendedScalars.DateTime
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@DgsComponent
class AccountDataFetcher(
    private val accountService: AccountService,
) {

    @DgsQuery
    fun accounts(): List<Account> {
        val accounts = accountService.findAll()
        return accounts
    }

    @DgsMutation
    fun createAccount(creation: AccountCreation): Account {
        return accountService.create(creation)
    }
}
