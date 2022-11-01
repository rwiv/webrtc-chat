package com.example.cloverchatserver.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class DefaultSecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/test/**").authenticated()
                .anyRequest().permitAll()
                .and()

        http.headers().frameOptions().disable()
        http.csrf().disable()

        return http.build()
    }
}