package com.example.chatbot.config.inteceptor

import com.example.chatbot.interceptor.UserIdInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig (
    private val userIdInterceptor: UserIdInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userIdInterceptor)
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/actuator/**", "/error/**", "/auth/**")
    }
}