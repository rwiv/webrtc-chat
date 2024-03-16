package com.github.cloverchatserver.common.graphql

import com.netflix.graphql.dgs.internal.method.ArgumentResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.core.MethodParameter
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class DgsAuthenticationArgumentResolver : ArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == Authentication::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, dfe: DataFetchingEnvironment): Any? {
        return SecurityContextHolder.getContext().authentication
    }
}
