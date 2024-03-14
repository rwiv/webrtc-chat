package com.github.cloverchatserver.security.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import com.github.cloverchatserver.security.handlers.DefaultFailureHandler
import com.github.cloverchatserver.security.handlers.DefaultSuccessHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component

@Component
class ApiLoginFilter(
    private val authenticationManager: AuthenticationManager,
    rememberMeServices: RememberMeServices,
    successHandler: DefaultSuccessHandler,
    failureHandler: DefaultFailureHandler,
) : AbstractAuthenticationProcessingFilter(
    AntPathRequestMatcher("/api/auth/login", "POST")
) {

    private val mapper: ObjectMapper = jacksonObjectMapper()

    init {
        super.setAuthenticationManager(authenticationManager)
        super.setAuthenticationSuccessHandler(successHandler)
        super.setAuthenticationFailureHandler(failureHandler)
        super.setRememberMeServices(rememberMeServices)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        val req = mapper.readValue(request.reader, LoginRequest::class.java)
        val requestToken = AuthenticationToken.requestToken(req.username, req.password)

        return authenticationManager.authenticate(requestToken) // return success token
    }
}