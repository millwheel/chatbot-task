package com.example.chatbot.exception.dto

import org.springframework.http.HttpStatus
import java.time.Instant

data class ErrorResult(
    val timestamp: Instant = Instant.now(),
    val status: HttpStatus,
    val message: String
) {
    companion object {
        fun of(status: HttpStatus, message: String): ErrorResult {
            return ErrorResult(
                status = status,
                message = message
            )
        }
    }
}