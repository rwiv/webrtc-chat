package com.example.cloverchatserver.security

import com.example.cloverchatserver.security.filter.RestApiLoginFilter
import com.example.cloverchatserver.security.filter.LoginFilterFailureHandler
import com.example.cloverchatserver.security.filter.LoginFilterSuccessHandler
import com.example.cloverchatserver.security.handler.DefaultAccessDeniedHandler
import com.example.cloverchatserver.security.handler.DefaultAuthenticationEntryPoint
import com.example.cloverchatserver.user.repository.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class DefaultSecurityConfig(

    val authenticationManager: AuthenticationManager,
    val successHandler: LoginFilterSuccessHandler,
    val failureHandler: LoginFilterFailureHandler,
    val deniedHandler: DefaultAccessDeniedHandler,
    val entryPoint: DefaultAuthenticationEntryPoint

) {

    val permitList = arrayOf("/user/register", "/h2-console/**", "/test/stomp", "/js/**")

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/test/hello").hasRole(Role.MEMBER.toString())
            .antMatchers("/test/admin").hasRole(Role.ADMIN.toString())
            .antMatchers(*permitList).permitAll()
            .anyRequest().authenticated()
            .and()
        .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .accessDeniedHandler(deniedHandler)
            .authenticationEntryPoint(entryPoint)

        http.headers().frameOptions().disable()
        http.csrf().disable()

        return http.build()
    }

    private fun loginFilter(): RestApiLoginFilter {
        val filter = RestApiLoginFilter()
        filter.setAuthenticationManager(authenticationManager)
        filter.setAuthenticationSuccessHandler(successHandler)
        filter.setAuthenticationFailureHandler(failureHandler)

        return filter
    }
}