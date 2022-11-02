package com.example.cloverchatserver.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginFilterFailureHandler : AuthenticationFailureHandler {

    private val mapper = jacksonObjectMapper()

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        if (response == null || request == null || exception == null) {
            throw RuntimeException("object is null!")
        }

        var errorMessage = "Invalid Username or Password"

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        when (exception) {
            is BadCredentialsException -> { errorMessage = "Invalid Username or Password" }
            is DisabledException -> { errorMessage = "Locked" }
            is CredentialsExpiredException -> { errorMessage = "Expired password" }
        }

        mapper.writeValue(response.writer, errorMessage)
    }
}