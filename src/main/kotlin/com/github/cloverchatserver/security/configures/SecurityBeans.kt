package com.github.cloverchatserver.security.configures

import com.github.cloverchatserver.security.userdetails.AccountDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices

@Configuration
class SecurityBeans(
    private val accountDetailsService: AccountDetailsService,
) {

//    @Bean
//    fun corsFilter(): FilterRegistrationBean<*> {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//
//        config.addAllowedOrigin("http://localhost:5173")
//        config.addAllowedOrigin("http://localhost:4173")
//
//        config.allowCredentials = true
//        config.allowedMethods = mutableListOf("POST", "OPTIONS", "GET", "DELETE", "PUT")
//        config.allowedHeaders = mutableListOf("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
//
//        source.registerCorsConfiguration("/**", config)
//
//        val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
//        bean.order = Ordered.HIGHEST_PRECEDENCE
//
//        return bean
//    }

    @Bean
    fun rememberMeServices(): RememberMeServices {
        val services = TokenBasedRememberMeServices("springRocks", accountDetailsService)
        services.parameter = "remember"
        services.setTokenValiditySeconds(3600 * 24 * 7)
        return services
    }
}