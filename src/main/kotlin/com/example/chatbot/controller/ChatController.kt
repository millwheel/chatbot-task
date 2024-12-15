package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.dto.chat.ChatResponse
import com.example.chatbot.service.chat.ChatService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
class ChatController (
    private val chatService: ChatService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createChat(
        @RequestAttribute userId: String,
        @RequestBody request: ChatRequest
    ): ChatResponse {
        val chat = chatService.createChat(userId, request.question)
        return ChatResponse.of(chat)
    }

}