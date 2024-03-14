package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.repository.Role

data class AccountDto(
    val id: Long,
    val email: String,
    val password: String,
    val nickname: String,
    val role: Role,
)
