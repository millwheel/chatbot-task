package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatThreadResponse
import com.example.chatbot.service.chat.ChatThreadService
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/treads")
class ChatThreadController (
    private val chatThreadService: ChatThreadService
) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    fun getAllThreads(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        val threads = chatThreadService.getAllTread(pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @PreAuthorize("hasRole('ADMIN, MEMBER')")
    @GetMapping("/me")
    fun getThreadsByUser(
        @RequestAttribute userId: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        val threads = chatThreadService.getThreadsByUser(userId, pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @PreAuthorize("hasRole('ADMIN, MEMBER')")
    @DeleteMapping("/{threadId}")
    fun deleteThread(
        @RequestAttribute userId: String,
        @PathVariable threadId: String
    ) {
        chatThreadService.deleteThread(userId, threadId)
    }
}