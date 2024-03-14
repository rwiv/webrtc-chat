package com.github.cloverchatserver.security.web

data class LoginRequest(
    val username: String,
    val password: String,
)
