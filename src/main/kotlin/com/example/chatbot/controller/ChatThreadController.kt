package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatThreadResponse
import com.example.chatbot.service.chat.ChatThreadService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/treads")
class ChatThreadController (
    private val chatThreadService: ChatThreadService
) {

    @GetMapping("/all")
    fun getAllThreads(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        val threads = chatThreadService.getAllTread(pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @GetMapping
    fun getThreadsByUser(
        @RequestAttribute userId: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        val threads = chatThreadService.getThreadsByUser(userId, pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @DeleteMapping("/{threadId}")
    fun deleteThread(
        @RequestAttribute userId: String,
        @PathVariable threadId: Long
    ) {
        chatThreadService.deleteThread(userId, threadId)
    }
}