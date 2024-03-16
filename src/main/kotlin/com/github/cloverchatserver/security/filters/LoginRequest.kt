package com.github.cloverchatserver.security.filters

import com.github.cloverchatserver.security.authentication.AuthenticationToken

data class LoginRequest(
    val username: String,
    val password: String,
) {
    companion object {
        fun of(requestedAuth: AuthenticationToken) = LoginRequest(
            username = requestedAuth.principal,
            password = requestedAuth.credentials!!
        )
    }
}
