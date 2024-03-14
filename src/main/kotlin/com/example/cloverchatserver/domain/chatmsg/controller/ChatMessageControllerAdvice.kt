package com.example.cloverchatserver.domain.chatmsg.controller

import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.example.cloverchatserver.chat.message.controller")
class ChatMessageControllerAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    fun handle(e: BadCredentialsException) =
        ErrorHelper.getResponseEntity(HttpStatus.UNAUTHORIZED, "BadCredentialsException")
}