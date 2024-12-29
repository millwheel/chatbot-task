package com.example.chatbot.config.security

import com.example.chatbot.config.security.component.RestAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManagerBuilder: AuthenticationManagerBuilder): SecurityFilterChain {
        val authenticationManager = authenticationManagerBuilder.build()

        http
            .authorizeHttpRequests { authorization -> authorization
                .requestMatchers( "/actuator/**", "/error/**", "/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/docs/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
            }
            .authenticationManager(authenticationManager)
            .addFilterBefore(getRestAuthorizationFilter(authenticationManager), UsernamePasswordAuthenticationFilter::class.java)
            .csrf {
                it.disable()
            }
        return http.build()
    }

    fun getRestAuthorizationFilter(authenticationManager: AuthenticationManager): RestAuthenticationFilter {
        val restAuthenticationFilter = RestAuthenticationFilter()
        restAuthenticationFilter.setAuthenticationManager(authenticationManager)
        return restAuthenticationFilter
    }

}