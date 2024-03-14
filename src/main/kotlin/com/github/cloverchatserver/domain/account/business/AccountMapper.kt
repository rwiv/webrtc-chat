package com.github.cloverchatserver.domain.account.business

import com.github.cloverchatserver.domain.account.persistence.Account
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun toDto(account: Account) = AccountDto(
        id = account.id!!,
        username = account.username,
        password = account.password,
        nickname = account.nickname,
        role = account.role,
    )
}