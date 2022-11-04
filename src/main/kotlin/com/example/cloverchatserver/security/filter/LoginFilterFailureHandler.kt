package com.example.cloverchatserver.security.filter

import com.example.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginFilterFailureHandler: AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        when (exception) {
            is BadCredentialsException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "BadCredentialsException"
            )
            is DisabledException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "DisabledException"
            )
            is CredentialsExpiredException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "CredentialsExpiredException"
            )
            else -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "AuthenticationException"
            )
        }
    }
}