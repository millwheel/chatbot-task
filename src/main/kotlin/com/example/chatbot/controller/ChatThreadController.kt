package com.example.chatbot.controller

import com.example.chatbot.dto.chat.ChatThreadResponse
import com.example.chatbot.service.chat.ChatThreadService
import com.example.chatbot.validator.PaginationValidator
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/treads")
class ChatThreadController (
    private val chatThreadService: ChatThreadService,
    private val paginationValidator: PaginationValidator
) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    fun getAllThreads(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        paginationValidator.validateParameter(pageIndex, pageSize, orderDirection)
        val threads = chatThreadService.getAllTreads(pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/me")
    fun getMyThreads(
        @RequestAttribute userId: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        paginationValidator.validateParameter(pageIndex, pageSize, orderDirection)
        val threads = chatThreadService.getThreadsByUser(userId, pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @DeleteMapping("/{threadId}")
    fun deleteThread(
        @RequestAttribute userId: String,
        @PathVariable threadId: String
    ) {
        chatThreadService.deleteThread(userId, threadId)
    }
}