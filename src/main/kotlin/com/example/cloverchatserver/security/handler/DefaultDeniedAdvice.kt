package com.example.cloverchatserver.security.handler

import com.example.cloverchatserver.common.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class DefaultDeniedAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ AccessDeniedException::class ])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseError {
        println(e::class.java)
        return ResponseError(HttpStatus.FORBIDDEN.value(), "AccessDeniedException")
    }
}