package com.github.cloverchatserver.domain.user.controller

import com.github.cloverchatserver.common.ErrorHelper
import com.github.cloverchatserver.domain.user.service.AccountNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.github.cloverchatserver.user.controller")
class UserControllerAdvice {

    @ExceptionHandler(value = [ AccountNotFoundException::class ])
    fun handle(e: AccountNotFoundException) =
        ErrorHelper.getResponseEntity(HttpStatus.NOT_FOUND, "UserNotFoundException")
}