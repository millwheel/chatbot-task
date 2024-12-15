package com.example.chatbot.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.writer.write(
            """
            {
                "timestamp": "${java.time.Instant.now()}",
                "status": 401,
                "error": "Unauthorized",
                "message": "Authentication is required to access this resource",
                "path": "${request.requestURI}"
            }
            """.trimIndent()
        )
    }
}
