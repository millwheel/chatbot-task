package com.example.chatbot.dto.user

import com.example.chatbot.entity.UserRole

data class SignupRequest (
    val email: String,
    val name: String,
    val password: String,
    val role: UserRole?
)