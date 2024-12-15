package com.example.chatbot.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

private const val SECRET_KEY = "this-is-a-very-secure-and-long-secret-key-256-bits"

private val secretKey: SecretKey = SecretKeySpec(Base64.getEncoder().encode(SECRET_KEY.toByteArray()), SignatureAlgorithm.HS256.jcaName)

fun generateToken(userId: String, email: String, role: String): String {
    val now = Date()
    val expiryDate = Date(now.time + 1000 * 60 * 60 * 24)
    return Jwts.builder()
        .setSubject(userId)
        .claim("email", email)
        .claim("role", role)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(
            secretKey, SignatureAlgorithm.HS256
        )
        .compact()
}

fun validateToken(token: String): String? {
    return try {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
        claims.subject
    } catch (e: Exception) {
        null
    }
}