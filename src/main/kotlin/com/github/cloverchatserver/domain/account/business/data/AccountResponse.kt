package com.github.cloverchatserver.domain.account.business.data

import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRole

data class AccountResponse(
    val id: Long,
    val role: AccountRole,
    val username: String,
    val nickname: String,
    val avatarUrl: String,
) {
    companion object {
        fun of(account: Account) = AccountResponse(
            account.id!!,
            account.role,
            account.username,
            account.nickname,
            account.avatarUrl,
        )
    }
}
