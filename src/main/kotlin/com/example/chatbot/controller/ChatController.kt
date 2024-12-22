package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.dto.chat.ChatResponse
import com.example.chatbot.service.chat.ChatService
import com.example.chatbot.service.log.ChatLogService
import com.example.chatbot.service.log.UserActivityLogService
import com.example.chatbot.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    private val userService: UserService,
    private val chatService: ChatService,
    private val chatLogService: ChatLogService,
    private val userActivityLogService: UserActivityLogService
) {

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createChat(
        @RequestAttribute userId: String,
        @RequestBody chatRequest: ChatRequest
    ): ChatResponse {
        val user = userService.getUserById(userId)
        val chat = chatService.createChat(userId, chatRequest)
        userActivityLogService.createChatCreationLog(userId, user.email)
        chatLogService.createChatLog(chat, user)
        return ChatResponse.of(chat)
    }

}