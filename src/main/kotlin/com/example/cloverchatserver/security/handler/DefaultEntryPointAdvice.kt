package com.example.cloverchatserver.security.handler

import com.example.cloverchatserver.common.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DefaultEntryPointAdvice {

    @ExceptionHandler(value = [ InsufficientAuthenticationException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInsufficientAuthenticationException(e: InsufficientAuthenticationException): ResponseError {
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "InsufficientAuthenticationException")
    }

    @ExceptionHandler(value = [ AuthenticationException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(e: AuthenticationException): ResponseError {
        println(e::class.java)
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "AuthenticationException")
    }
}