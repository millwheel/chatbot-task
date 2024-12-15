package com.example.chatbot.util

import com.example.chatbot.sender.dto.OpenaiResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun parseAnswerFromResponse(responseJson: String): String {
    val objectMapper = jacksonObjectMapper()
    val openaiResponse: OpenaiResponse = objectMapper.readValue(responseJson)

    return openaiResponse.choices.firstOrNull()?.message?.content
        ?: throw RuntimeException("No content found in response")
}