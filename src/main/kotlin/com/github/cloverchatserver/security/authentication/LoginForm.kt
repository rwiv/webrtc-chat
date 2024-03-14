package com.github.cloverchatserver.security.authentication

data class LoginForm(
    val username: String,
    val password: String?,
) {
    companion object {
        fun of(requestedAuth: AuthenticationToken) = LoginForm(
            username = requestedAuth.principal,
            password = requestedAuth.credentials
        )
    }
}
