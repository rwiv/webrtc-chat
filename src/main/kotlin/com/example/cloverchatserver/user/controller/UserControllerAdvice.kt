package com.example.cloverchatserver.user.controller

import com.example.cloverchatserver.common.ResponseError
import com.example.cloverchatserver.user.service.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [ UserController::class ])
class UserControllerAdvice {

    @ExceptionHandler(value = [ UserNotFoundException::class ])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(e: UserNotFoundException): ResponseError {
        return ResponseError(HttpStatus.NOT_FOUND.value(), "UserNotFoundException")
    }
}