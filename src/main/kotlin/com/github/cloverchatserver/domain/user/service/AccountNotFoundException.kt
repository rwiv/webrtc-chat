package com.github.cloverchatserver.domain.user.service

class AccountNotFoundException(
    message: String = "User Not Found!"
) : RuntimeException(message) {}