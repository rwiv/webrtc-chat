package com.github.cloverchatserver.security.web

import com.github.cloverchatserver.error.exception.HttpException
import com.github.cloverchatserver.error.resolver.HttpErrorSender
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class ExceptionHandlerFilter(private val sender: HttpErrorSender) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (throwable: Throwable) {
            val ex = if (throwable is HttpException) {
                throwable
            } else {
                HttpException(HttpStatus.UNAUTHORIZED.value(), throwable.message, throwable)
            }
            sender.send(
                request as HttpServletRequest,
                response as HttpServletResponse,
                ex,
            )
        }
    }
}