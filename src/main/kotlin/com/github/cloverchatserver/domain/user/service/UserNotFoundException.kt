package com.github.cloverchatserver.domain.user.service

class UserNotFoundException(
    message: String = "User Not Found!"
) : RuntimeException(message) {}