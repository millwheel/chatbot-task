package com.example.chatbot.dto.feedback

data class FeedbackCreateRequest(
    val chatId: String,
    val isPositive: Boolean
)
