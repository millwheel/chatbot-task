package com.example.chatbot.sender.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Choice(
    val index: Int,
    val message: Message?,
    val logprobs: Any?,
    @JsonProperty("finish_reason")
    val finishReason: String
)