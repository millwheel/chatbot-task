package com.example.chatbot.dto.user

data class LoginSuccessResponse(
    val userId: String,
    val email: String,
    val token: String
)
