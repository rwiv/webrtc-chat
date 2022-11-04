package com.example.cloverchatserver.security.filter

import com.example.cloverchatserver.common.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [ LoginFilterFailureHandler::class ])
class AuthenticationFailureAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadCredentialsException(): ResponseError {
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "Invalid Username or Password")
    }

    @ExceptionHandler(value = [ DisabledException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleDisabledException(): ResponseError {
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "Locked")
    }

    @ExceptionHandler(value = [ CredentialsExpiredException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleCredentialsExpiredException(): ResponseError {
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "Expired password")
    }

    @ExceptionHandler(value = [ AuthenticationException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(e: AuthenticationException): ResponseError {
        println(e::class.java)
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "Authentication error")
    }
}