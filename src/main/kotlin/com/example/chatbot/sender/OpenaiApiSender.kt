package com.example.chatbot.sender

import com.example.chatbot.sender.dto.OpenaiRequest
import com.example.chatbot.sender.dto.ChatMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OpenaiApiSender(
    @Value("\${openai.key}")
    private val openaiKey: String
) {

    private val restClient = RestClient.builder()
        .baseUrl("https://api.openai.com/v1/chat/completions")
        .defaultHeader("Authorization", "Bearer $openaiKey")
        .defaultHeader("Content-Type", "application/json")
        .build()

    private val webClient = WebClient.builder()
        .baseUrl("https://api.openai.com/v1/chat/completions")
        .defaultHeader("Authorization", "Bearer $openaiKey")
        .defaultHeader("Content-Type", "application/json")
        .build()


    fun sendRequestAndGetResponse(text: String, model: String): String {
        val result = restClient.post()
            .body(buildRequestBody(text, model, false))
            .retrieve()
            .onStatus({ it.isError }) { _, response ->
                throw RuntimeException("Error with code: ${response.statusCode}, with header: ${response.headers}")
            }
            .body(String::class.java)

        return result!!
    }

    fun sendRequestAndStreamResponse(text: String, model: String): Flux<String> {
        return webClient.post()
            .bodyValue(buildRequestBody(text, model, true))
            .retrieve()
            .onStatus({ it.isError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap { errorBody -> throw RuntimeException("API Error: $errorBody") }
            }
            .bodyToFlux(String::class.java)
    }

    private fun buildRequestBody(text: String, model: String, stream: Boolean): OpenaiRequest {
        return OpenaiRequest(
            model = model,
            messages = listOf(
                ChatMessage(role = "system", content = "You are assistant."),
                ChatMessage(role = "user", content = text)
            ),
            stream = stream
        )
    }
}
