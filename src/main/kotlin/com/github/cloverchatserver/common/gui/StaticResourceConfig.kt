package com.github.cloverchatserver.common.gui

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class StaticResourceConfig : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/dist/**")
            .addResourceLocations("classpath:/dist/")
            .setCachePeriod(20)

        registry.addResourceHandler("/assets/**")
            .addResourceLocations("classpath:/dist/assets/")
            .setCachePeriod(20)

        registry.addResourceHandler("/vite.svg")
            .addResourceLocations("classpath:/dist/vite.svg")
            .setCachePeriod(20)
    }
}
