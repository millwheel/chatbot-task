package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.dto.chat.ChatResponse
import com.example.chatbot.service.chat.ChatService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    private val chatService: ChatService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createChat(
        @RequestAttribute userId: String,
        @RequestBody chatRequest: ChatRequest
    ): ChatResponse {
        val chat = chatService.createChat(userId, chatRequest)
        return ChatResponse.of(chat)
    }

}