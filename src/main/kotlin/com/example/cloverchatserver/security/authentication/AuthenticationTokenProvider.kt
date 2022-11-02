package com.example.cloverchatserver.security.authentication

import com.example.cloverchatserver.security.exception.AuthenticationNotFoundException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthenticationTokenProvider(

    val userDetailsService: UserPrincipalService,
    val passwordEncoder: PasswordEncoder

) : AuthenticationProvider {

    @Transactional
    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication as AuthenticationToken?
            ?: throw AuthenticationNotFoundException("Authentication not found")

        val requestEmail = token.principal
        val requestPassword = token.credentials

        val userPrincipal: UserPrincipal = userDetailsService.loadUserByUsername(requestEmail)

        if (!passwordEncoder.matches(requestPassword, userPrincipal.password)) {
            throw BadCredentialsException("Invalid password")
        }

        val successToken = AuthenticationToken(
            userPrincipal.username,
            userPrincipal.authorities,
            userPrincipal.user.toResponseUser()
        )

        return successToken
    }

    override fun supports(authentication: Class<*>?): Boolean {
        if (authentication == null) {
            throw AuthenticationNotFoundException()
        }

        return authentication == AuthenticationToken::class.java
    }
}