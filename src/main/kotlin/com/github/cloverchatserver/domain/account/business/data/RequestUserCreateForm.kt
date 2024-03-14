package com.github.cloverchatserver.domain.account.business.data

import com.github.cloverchatserver.domain.account.persistence.AccountRole
import com.github.cloverchatserver.domain.account.persistence.Account
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