package com.github.cloverchatserver.security.handlers

import com.github.cloverchatserver.common.error.exception.HttpException
import com.github.cloverchatserver.common.error.resolver.HttpErrorSender
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class DefaultFailureHandler(private val sender: HttpErrorSender) : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        when (exception) {
            is UsernameNotFoundException -> sender.send(
                request, response, HttpException(HttpStatus.UNAUTHORIZED.value(), exception.message, exception)
            )
            is BadCredentialsException -> sender.send(
                request, response, HttpException(HttpStatus.UNAUTHORIZED.value(), exception.message, exception)
            )
            is DisabledException -> sender.send(
                request, response, HttpException(HttpStatus.UNAUTHORIZED.value(), exception.message, exception)
            )
            is CredentialsExpiredException -> sender.send(
                request, response, HttpException(HttpStatus.UNAUTHORIZED.value(), exception.message, exception)
            )
            else -> sender.send(
                request, response, HttpException(HttpStatus.UNAUTHORIZED.value(), exception.message, exception)
            )
        }
    }
}