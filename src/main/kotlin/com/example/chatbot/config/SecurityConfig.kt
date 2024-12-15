package com.example.chatbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
){

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorization -> authorization
                .requestMatchers( "/actuator/**", "/error/**", "/user/**").permitAll() // TODO user api 접근 권한 수정
                .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .csrf{
                it.disable()
            }
        return http.build()
    }

}