package com.example.cloverchatserver.security.filter

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthenticationFailureAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadCredentialsException(): String {
        return "Invalid Username or Password"
    }

    @ExceptionHandler(value = [ DisabledException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleDisabledException(): String {
        return "Locked"
    }

    @ExceptionHandler(value = [ CredentialsExpiredException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleCredentialsExpiredException(): String {
        return "Expired password"
    }
}