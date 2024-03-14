package com.github.cloverchatserver.error.resolver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.cloverchatserver.error.resolver.HttpErrorResolver
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

/**
 * spring security 등 Controller를 사용하지 않고 에러를 http로 전송하기 위한 class
 */
@Component
class HttpErrorSender(val resolver: HttpErrorResolver) {

    fun send(request: HttpServletRequest, response: HttpServletResponse, throwable: Throwable) {
        val mapper = jacksonObjectMapper()
        val err = resolver.toHttpError(throwable, request)
        resolver.printErrorInfo(err)

        response.status = err.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "utf-8" // 이게 없으면 한글이 깨진다

        mapper.writeValue(response.writer, err.toResponse())
    }
}