package com.example.chatbot.sender.dto

data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>
)