package com.example.chatbot.dto.chat

import jakarta.validation.constraints.NotBlank

data class ChatRequest (
    @field:NotBlank
    val question: String,
    val model: String?
)