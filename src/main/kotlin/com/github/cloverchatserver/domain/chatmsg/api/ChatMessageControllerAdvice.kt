package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.github.cloverchatserver.chat.message.controller")
class ChatMessageControllerAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    fun handle(e: BadCredentialsException) =
        ErrorHelper.getResponseEntity(HttpStatus.UNAUTHORIZED, "BadCredentialsException")
}