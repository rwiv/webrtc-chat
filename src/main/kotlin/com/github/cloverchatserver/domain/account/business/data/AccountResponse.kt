package com.github.cloverchatserver.domain.account.business.data

import com.github.cloverchatserver.domain.account.persistence.Account

data class AccountResponse(
    val id: Long,
    val username: String,
    val nickname: String
) {
    companion object {
        fun of(account: Account) = AccountResponse(
            account.id!!,
            account.username,
            account.nickname,
        )
    }
}
