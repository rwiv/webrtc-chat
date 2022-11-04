package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.service.ChatRoomNotFoundException
import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [ ChatRoomController::class ])
class ChatRoomControllerAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    fun handle(e: BadCredentialsException) =
        ErrorHelper.getResponseEntity(HttpStatus.UNAUTHORIZED, "BadCredentialsException")

    @ExceptionHandler(value = [ ChatRoomNotFoundException::class ])
    fun handle(e: ChatRoomNotFoundException) =
        ErrorHelper.getResponseEntity(HttpStatus.NOT_FOUND, "ChatRoomNotFoundException")
}