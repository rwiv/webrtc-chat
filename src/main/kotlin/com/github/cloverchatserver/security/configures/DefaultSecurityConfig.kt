package com.github.cloverchatserver.security.configures

import com.github.cloverchatserver.security.filters.ApiLoginFilter
import com.github.cloverchatserver.security.filters.DevAuthFilter
import com.github.cloverchatserver.security.filters.ExceptionHandlerFilter
import com.github.cloverchatserver.security.handlers.DefaultAccessDeniedHandler
import com.github.cloverchatserver.security.handlers.DefaultAuthenticationEntryPoint
import com.github.cloverchatserver.security.userdetails.AccountDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class DefaultSecurityConfig(
    private val devAuthFilter: DevAuthFilter,
    private val loginFilter: ApiLoginFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val deniedHandler: DefaultAccessDeniedHandler,
    private val entryPoint: DefaultAuthenticationEntryPoint,
    private val accountDetailsService: AccountDetailsService,
    private val rememberMeServices: RememberMeServices,
) {

    val permitList = listOf(
        "/",
        "/dev/**",
        "/api/auth/signup",
        "/graphiql/**",
    )
    val ignoreList = listOf(
        "/avatars/**",
    )

    private fun wrapMatcher(list: List<String>): Array<AntPathRequestMatcher> {
        return list.map { AntPathRequestMatcher(it) }.toTypedArray()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { conf -> conf
            .requestMatchers(*wrapMatcher(permitList)).permitAll()
//            .requestMatchers(AntPathRequestMatcher("/dev/account")).hasAuthority(AccountRole.ADMIN.name)
//            .requestMatchers(AntPathRequestMatcher("/dev/account")).anonymous()
//            .requestMatchers(AntPathRequestMatcher("/dev/account")).authenticated()
            .anyRequest().authenticated()
//            .anyRequest().permitAll()
        }

        http.logout { logout -> logout
            .logoutUrl("/api/auth/logout")
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "remember-me")
//            .addLogoutHandler { request, response, authentication -> println("logout") }
            .logoutSuccessHandler { request, response, authentication -> }
        }

        http.rememberMe { remember -> remember
            .rememberMeServices(rememberMeServices)
            .userDetailsService(accountDetailsService)
        }

        http.addFilterBefore(devAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(loginFilter, DevAuthFilter::class.java)
        http.addFilterBefore(exceptionHandlerFilter, ApiLoginFilter::class.java)

        http.exceptionHandling { conf -> conf
            .accessDeniedHandler(deniedHandler)
            .authenticationEntryPoint(entryPoint)
        }

        http.csrf { conf -> conf.disable() }

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web
            .ignoring()
            .requestMatchers(*wrapMatcher(ignoreList))
            .requestMatchers("/h2-console")
        }
    }
}