package com.example.cloverchatserver.user.service

class UserNotFoundException(
    message: String = "User Not Found!"
) : RuntimeException(message) {}