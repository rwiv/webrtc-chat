package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.chatuser.business.DuplicatedChatUserException
import com.github.cloverchatserver.domain.chatuser.business.NotFoundChatUserException
import com.github.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.github.cloverchatserver.chat.user.controller")
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