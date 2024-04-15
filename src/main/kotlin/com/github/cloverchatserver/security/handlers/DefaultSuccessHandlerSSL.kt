package com.github.cloverchatserver.security.handlers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Component

//@Component
class DefaultSuccessHandlerSSL(
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

        if (response.headerNames.contains(HttpHeaders.SET_COOKIE)) {
            val jSessionValue = extractJSessionId(response)
                ?: throw RuntimeException("not found JSESSIONID")
            val cookie = ResponseCookie.from("JSESSIONID")
                .value(jSessionValue)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build()
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString())
        }

        mapper.writeValue(response.writer, "login complete")
    }

    private fun extractJSessionId(response: HttpServletResponse): String? {
        val cookieStr = response.getHeader(HttpHeaders.SET_COOKIE)
        val match = Regex("JSESSIONID=(.*?);").find(cookieStr)?.groupValues
        if (match !== null && match.size > 1) {
            return match[1]
        }
        return null
    }
}
