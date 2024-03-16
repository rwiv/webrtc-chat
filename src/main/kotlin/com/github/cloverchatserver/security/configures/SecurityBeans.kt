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

    @Bean
    fun rememberMeServices(): RememberMeServices {
        val services = TokenBasedRememberMeServices("springRocks", accountDetailsService)
        services.parameter = "remember"
        services.setTokenValiditySeconds(3600 * 24 * 7)
        return services
    }
}