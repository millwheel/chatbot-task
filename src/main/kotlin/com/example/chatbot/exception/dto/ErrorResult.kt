package com.example.chatbot.exception.dto

import org.springframework.http.HttpStatus

data class ErrorResult(
    val code: String,
    val message: String
) {
    companion object {
        fun of(status: HttpStatus, message: String): ErrorResult {
            return ErrorResult(
                code = status.reasonPhrase,
                message = message
            )
        }
    }
}