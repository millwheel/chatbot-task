package com.example.chatbot.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = "application/json"
        response.writer.write(
            """
            {
                "timestamp": "${java.time.Instant.now()}",
                "status": 403,
                "error": "Forbidden",
                "message": "You do not have permission to access this resource",
                "path": "${request.requestURI}"
            }
            """.trimIndent()
        )
    }
}
