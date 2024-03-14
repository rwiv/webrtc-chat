package com.example.cloverchatserver.domain.user.controller

import com.example.cloverchatserver.common.ErrorHelper
import com.example.cloverchatserver.domain.user.service.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.example.cloverchatserver.user.controller")
class UserControllerAdvice {

    @ExceptionHandler(value = [ UserNotFoundException::class ])
    fun handle(e: UserNotFoundException) =
        ErrorHelper.getResponseEntity(HttpStatus.NOT_FOUND, "UserNotFoundException")
}