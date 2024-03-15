package com.github.cloverchatserver.domain.account.business.data

import com.github.cloverchatserver.domain.account.persistence.AccountRole

data class AccountCreation(
    val role: AccountRole,
    val username: String,
    val password: String,
    val nickname: String
)