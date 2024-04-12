package com.github.cloverchatserver.security.handlers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Component

@Profile("prod")
@Component
class DefaultSuccessHandlerProd(
    val securityContextRepository: SecurityContextRepository
) : DefaultSuccessHandler {


    private val mapper = jacksonObjectMapper()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val securityContext = SecurityContextHolder.getContext()
        securityContext.authentication = authentication
        securityContextRepository.saveContext(securityContext, request, response)

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        mapper.writeValue(response.writer, "login complete")
    }
}
