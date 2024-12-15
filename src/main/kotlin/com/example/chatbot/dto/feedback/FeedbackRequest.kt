package com.example.chatbot.dto.feedback

data class FeedbackRequest(
    val chatId: String,
    val isPositive: Boolean
)
