package com.example.chatbot.config.security.component

import com.example.chatbot.dto.user.LoginRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class RestAuthenticationFilter: AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/login", "POST")) {

    private val objectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        if (request!!.method != HttpMethod.POST.name() || request.getHeader("X-Requested-With") != "XMLHttpRequest") {
            throw IllegalArgumentException("Authentication method is not supported")
        }

        val loginRequest: LoginRequest = objectMapper.readValue(request.inputStream)
        val restAuthenticationToken = RestAuthenticationToken(loginRequest.email, loginRequest.password)

        return authenticationManager.authenticate(restAuthenticationToken)
    }

}