package com.github.cloverchatserver.security.account

import com.github.cloverchatserver.domain.account.business.AccountDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountDetails(
    val account: AccountDto,
    roles: List<GrantedAuthority>
) : User(
    account.username,
    account.password,
    true, true, true, true,
    roles
)
