package com.example.chatbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ChatbotApplication

fun main(args: Array<String>) {
    runApplication<ChatbotApplication>(*args)
}
