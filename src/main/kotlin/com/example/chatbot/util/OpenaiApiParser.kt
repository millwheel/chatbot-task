package com.example.chatbot.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun extractAnswerFromResponse(responseJson: String): String {
    val objectMapper = jacksonObjectMapper()
    val root: JsonNode = objectMapper.readTree(responseJson)
    val content = root.get("choices")
        .get(0)
        ?.path("message")
        ?.path("content")
        ?.asText() ?: ""
    return content
}

fun extractAnswerFromResponseStream(responseJson: String): String {
    val objectMapper = jacksonObjectMapper()
    val root: JsonNode = objectMapper.readTree(responseJson)
    val content = root.path("choices")
        .get(0)
        ?.path("delta")
        ?.path("content")
        ?.asText() ?: ""
    return content
}