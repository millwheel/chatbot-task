package com.example.chatbot.dto.report


data class ChatReportLogResponse(
    val question: String,
    val answer: String,
    val userEmail: String,
)
