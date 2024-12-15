package com.example.chatbot.sender.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenaiResponse(
    val id: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    @JsonProperty("system_fingerprint")
    val systemFingerprint: String
)