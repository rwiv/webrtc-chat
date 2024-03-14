package com.github.cloverchatserver.security.userdetails

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.common.error.exception.NotFoundException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountDetailsService(
    private val accountService: AccountService,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): AccountDetails {
        val account = accountService.findByUsername(username)
            ?: throw NotFoundException("not found account")

        val roles = ArrayList<GrantedAuthority>().apply {
            add(SimpleGrantedAuthority(account.role.name))
        }

        return AccountDetails(account, roles)
    }
}
