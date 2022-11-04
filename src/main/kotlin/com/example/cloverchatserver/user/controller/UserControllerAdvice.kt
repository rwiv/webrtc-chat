package com.example.cloverchatserver.user.controller

import com.example.cloverchatserver.common.ErrorHelper
import com.example.cloverchatserver.user.service.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [ UserController::class ])
class UserControllerAdvice {

    @ExceptionHandler(value = [ UserNotFoundException::class ])
    fun handle(e: UserNotFoundException) =
        ErrorHelper.getResponseEntity(HttpStatus.NOT_FOUND, "UserNotFoundException")
}