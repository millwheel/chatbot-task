package com.example.chatbot.sender.dto

data class OpenaiRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val stream: Boolean
)