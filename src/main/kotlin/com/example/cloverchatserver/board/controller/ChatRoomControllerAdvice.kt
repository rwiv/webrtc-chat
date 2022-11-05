package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.service.ChatRoomNotFoundException
import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("com.example.cloverchatserver.board.controller")
class ChatRoomControllerAdvice {

    @ExceptionHandler(value = [ AccessDeniedException::class ])
    fun handle(e: AccessDeniedException) =
        ErrorHelper.getResponseEntity(HttpStatus.FORBIDDEN, "AccessDeniedException")

    @ExceptionHandler(value = [ ChatRoomNotFoundException::class ])
    fun handle(e: ChatRoomNotFoundException) =
        ErrorHelper.getResponseEntity(HttpStatus.NOT_FOUND, "ChatRoomNotFoundException")
}