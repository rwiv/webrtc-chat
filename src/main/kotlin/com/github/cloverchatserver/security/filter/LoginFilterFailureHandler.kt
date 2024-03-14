package com.github.cloverchatserver.security.filter

import com.github.cloverchatserver.common.ErrorHelper
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
class LoginFilterFailureHandler: AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        when (exception) {
            is UsernameNotFoundException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "Failure: UsernameNotFoundException"
            )
            is BadCredentialsException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "Failure: BadCredentialsException"
            )
            is DisabledException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "Failure: DisabledException"
            )
            is CredentialsExpiredException -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "Failure: CredentialsExpiredException"
            )
            else -> ErrorHelper.sendError(response,
                HttpStatus.UNAUTHORIZED, "Failure: ${exception.message}"
            )
        }
    }
}