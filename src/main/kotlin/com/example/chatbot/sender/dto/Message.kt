package com.example.chatbot.sender.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
    val role: String,
    val content: String,
    val refusal: String?
)