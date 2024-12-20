package com.example.chatbot.config.security.component

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtManager (
    @Value("\${jwt.secret:this-is-a-very-secure-and-long-secret-key-256-bits}")
    private val secretKeyString: String,
    @Value("\${jwt.expiration-ms:86400000}")
    private val expirationMs: Long
) {

    private val secretKey: SecretKey = SecretKeySpec(
        Base64.getEncoder().encode(secretKeyString.toByteArray()), SignatureAlgorithm.HS256.jcaName
    )

    fun generateToken(userId: String, email: String, role: String): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(userId)
            .claim("email", email)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationMs))
            .signWith(
                secretKey, SignatureAlgorithm.HS256
            )
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }

}