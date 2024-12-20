package com.example.chatbot.config.security.component

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtManager: JwtManager
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val securityContext = SecurityContextHolder.getContext()
        val token = request.getHeader("Authorization")?.substringAfter("Bearer ")

        if (token != null && jwtManager.validateToken(token)) {
            val claims = jwtManager.getClaims(token)
            val email = claims?.subject
            val role = claims?.get("role")

            if (email != null && role != null && securityContext.authentication == null) {
                val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))
                val authentication = UsernamePasswordAuthenticationToken(email, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                securityContext.authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}