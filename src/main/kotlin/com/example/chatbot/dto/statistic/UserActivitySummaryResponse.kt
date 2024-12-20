package com.example.chatbot.dto.statistic

data class UserActivitySummaryResponse (
    val signUp: Int,
    val login: Int,
    val chatCreation: Int
)