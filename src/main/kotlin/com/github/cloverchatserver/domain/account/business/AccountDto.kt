package com.github.cloverchatserver.domain.account.business

import com.github.cloverchatserver.domain.account.persistence.AccountRole

data class AccountDto(
    val id: Long,
    val username: String,
    val password: String,
    val nickname: String,
    val role: AccountRole,
)
