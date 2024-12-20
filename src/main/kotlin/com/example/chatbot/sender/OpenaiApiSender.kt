package com.example.chatbot.sender

import com.example.chatbot.sender.dto.OpenaiRequest
import com.example.chatbot.sender.dto.ChatMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class OpenaiApiSender(
    @Value("\${openai.key}")
    private val openaiKey: String
) {

    private var restClient = RestClient.builder()
        .baseUrl("https://api.openai.com/v1/chat/completions")
        .defaultHeader("Authorization", "Bearer $openaiKey")
        .defaultHeader("Content-Type", "application/json")
        .build()

    fun sendRequestAndGetResponse(text: String, model: String): String{
        val result = restClient.post()
            .body(buildRequestBody(text, model))
            .retrieve()
            .onStatus({ status -> status.is4xxClientError }) { request, response ->
                throw RuntimeException("Error with code: ${response.statusCode}, with header: ${response.headers}")
            }
            .body(String::class.java)

        return result!!
    }

    private fun buildRequestBody(text: String, model: String): OpenaiRequest {
        return OpenaiRequest(
            model = model,
            messages = listOf(
                ChatMessage(role = "system", content = "You are assistant."),
                ChatMessage(role = "user", content = text)
            )
        )
    }
}
