package com.github.cloverchatserver.security.filters

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.persistence.AccountRole
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Profile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Profile("dev")
@Component
class DevAuthFilter(
    private val accountService: AccountService,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val matcher = AntPathRequestMatcher("/**")
        val match = matcher.matcher(request as HttpServletRequest)
        if (!match.isMatch) {
            return chain.doFilter(request, response)
        }

        val securityContext = SecurityContextHolder.getContext()
        if (securityContext?.authentication?.isAuthenticated == true) {
            return chain.doFilter(request, response)
        }

        val reqApiKey: String = request.getHeader("Authorization") ?: ""
        if (reqApiKey == "admin") {
//        if (reqApiKey == "admin" || request.servletPath.startsWith("/graphql")) {
            val account = accountService.findAll().first { it.role == AccountRole.ADMIN }
            val roles = ArrayList<GrantedAuthority>().apply {
                add(SimpleGrantedAuthority(account.role.name))
            }
            val accountResponse = AccountResponse.of(account)
            val successToken = AuthenticationToken.successToken(
                account.username, roles, accountResponse,
            )
            securityContext.authentication = successToken
        }

        chain.doFilter(request, response)
    }
}
