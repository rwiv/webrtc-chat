package com.github.cloverchatserver.domain.user.controller.domain

import com.github.cloverchatserver.domain.user.repository.AccountRole
import com.github.cloverchatserver.domain.user.repository.Account
import org.springframework.security.crypto.password.PasswordEncoder

data class RequestUserCreateForm(

    val username: String,
    val password: String,
    val nickname: String

) {
    fun toUser(passwordEncoder: PasswordEncoder): Account = Account(
        null, AccountRole.MEMBER, username, passwordEncoder.encode(password), nickname
    )
}