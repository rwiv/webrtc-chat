package com.github.cloverchatserver.domain.account.business

class AccountNotFoundException(
    message: String = "User Not Found!"
) : RuntimeException(message) {}