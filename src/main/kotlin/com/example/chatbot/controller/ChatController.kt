package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.dto.chat.ChatResponse
import com.example.chatbot.service.chat.ChatService
import com.example.chatbot.service.log.UserActivityLogService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    private val chatService: ChatService,
    private val userActivityLogService: UserActivityLogService
) {

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createChat(
        @RequestAttribute userId: String,
        @RequestBody chatRequest: ChatRequest
    ): ChatResponse {
        val chat = chatService.createChat(userId, chatRequest)
        userActivityLogService.createChatCreationLog(userId)
        return ChatResponse.of(chat)
    }

}