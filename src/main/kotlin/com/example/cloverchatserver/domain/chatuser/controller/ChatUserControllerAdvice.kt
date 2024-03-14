package com.example.cloverchatserver.domain.chatuser.controller

import com.example.cloverchatserver.domain.chatuser.service.DuplicatedChatUserException
import com.example.cloverchatserver.domain.chatuser.service.NotFoundChatUserException
import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.example.cloverchatserver.chat.user.controller")
class ChatUserControllerAdvice {

    @ExceptionHandler(value = [ DuplicatedChatUserException::class ])
    fun handle(e: DuplicatedChatUserException) =
        ErrorHelper.getResponseEntity(HttpStatus.FORBIDDEN, "DuplicatedChatUserException")

    @ExceptionHandler(value = [ NotFoundChatUserException::class ])
    fun handle(e: NotFoundChatUserException) =
        ErrorHelper.getResponseEntity(HttpStatus.FORBIDDEN, "NotFoundChatUserException")

    @ExceptionHandler(value = [ AccessDeniedException::class ])
    fun handle(e: AccessDeniedException) =
        ErrorHelper.getResponseEntity(HttpStatus.FORBIDDEN, "AccessDeniedException")
}