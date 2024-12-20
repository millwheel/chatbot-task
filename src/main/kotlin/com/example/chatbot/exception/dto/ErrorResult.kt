package com.example.chatbot.exception.dto

import org.springframework.http.HttpStatus

data class ErrorResult(
    val code: HttpStatus,
    val message: String
) {
    companion object {
        fun of(status: HttpStatus, message: String): ErrorResult {
            return ErrorResult(
                code = status,
                message = message
            )
        }
    }
}