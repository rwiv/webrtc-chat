package com.example.cloverchatserver.security.handler

import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class DefaultExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ AuthenticationException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(): String {
        //Failure, EntryPoint Handler
        return "auth"
    }

    @ExceptionHandler(value = [ AccessDeniedException::class ])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(): String {
        //Denied Handler
        return "denied"
    }

    @ExceptionHandler(value = [ RuntimeException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleRuntimeException(): String {
        println("exception")
        return "exception"
    }
}