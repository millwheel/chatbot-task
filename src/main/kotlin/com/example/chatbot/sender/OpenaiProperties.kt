package com.example.chatbot.sender

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
class OpenaiProperties {
    lateinit var key: String
}