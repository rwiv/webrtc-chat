package com.example.cloverchatserver.user.repository

class UserNotFoundException(

    message: String = "User Not Found!"

) : RuntimeException(message) {}