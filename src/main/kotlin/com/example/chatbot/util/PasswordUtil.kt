package com.example.chatbot.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

private val encoder = BCryptPasswordEncoder()

fun encodePassword(password: String): String {
    return encoder.encode(password)
}

fun matchesPassword(rawPassword: String, encodedPassword: String): Boolean {
    return encoder.matches(rawPassword, encodedPassword)
}