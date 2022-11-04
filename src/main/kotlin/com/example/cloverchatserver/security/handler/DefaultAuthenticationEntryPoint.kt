package com.example.cloverchatserver.security.handler

import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DefaultAuthenticationEntryPoint: AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        when (authException) {
            is InsufficientAuthenticationException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "InsufficientAuthenticationException"
            )
            else -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "AuthenticationException"
            )
        }
    }
}