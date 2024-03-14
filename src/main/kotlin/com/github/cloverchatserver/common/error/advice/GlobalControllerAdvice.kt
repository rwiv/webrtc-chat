package com.github.cloverchatserver.common.error.advice

import com.projectd.bootapi.error.data.HttpErrorResponse
import com.github.cloverchatserver.common.error.resolver.HttpErrorResolver
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 개발할때 이 Advice를 켜면 stack trace가 로그에 표시되지 않기에 개발이 어려워짐
@Profile("stage", "prod")
@RestControllerAdvice
class GlobalControllerAdvice(val resolver: HttpErrorResolver) {

    @ExceptionHandler
    fun handle(throwable: Throwable, req: HttpServletRequest): ResponseEntity<HttpErrorResponse> {
        val err = resolver.toHttpError(throwable, req)
        return err.toResponseEntity()
    }
}