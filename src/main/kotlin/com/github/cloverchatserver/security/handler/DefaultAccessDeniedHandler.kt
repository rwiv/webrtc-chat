package com.github.cloverchatserver.security.handler

import com.github.cloverchatserver.common.ErrorHelper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DefaultAccessDeniedHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        ErrorHelper.sendError(response, HttpStatus.FORBIDDEN, "AccessDeniedException")
    }
}