package com.example.cloverchatserver.security.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DefaultAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val mapper = jacksonObjectMapper()

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        if (response == null) {
            throw RuntimeException("response object is null!")
        }

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()

        response.writer.write(mapper.writeValueAsString(HttpServletResponse.SC_UNAUTHORIZED))
    }
}