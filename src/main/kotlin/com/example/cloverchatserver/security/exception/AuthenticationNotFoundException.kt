package com.example.cloverchatserver.security.exception

class AuthenticationNotFoundException(

    message: String = "Authentication object Not Found!"

) : RuntimeException(message) {}