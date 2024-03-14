package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.repository.AccountRole

data class AccountDto(
    val id: Long,
    val username: String,
    val password: String,
    val nickname: String,
    val role: AccountRole,
)
