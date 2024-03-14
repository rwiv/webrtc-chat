package com.github.cloverchatserver.security.filters

data class LoginRequest(
    val username: String,
    val password: String,
)
