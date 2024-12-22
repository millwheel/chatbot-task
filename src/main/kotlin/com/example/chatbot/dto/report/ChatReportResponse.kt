package com.example.chatbot.dto.report

data class ChatReportResponse(
    val question: String,
    val answer: String,
    val userEmail: String
)
