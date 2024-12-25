package com.example.chatbot.controller

import com.example.chatbot.config.security.component.CustomPrincipal
import com.example.chatbot.dto.chat.ChatThreadResponse
import com.example.chatbot.service.chat.ChatThreadService
import com.example.chatbot.validator.PaginationValidator
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
        @AuthenticationPrincipal principal: CustomPrincipal,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<ChatThreadResponse> {
        paginationValidator.validateParameter(pageIndex, pageSize, orderDirection)
        val threads = chatThreadService.getThreadsByUser(principal.userId, pageIndex, pageSize, orderDirection)
        return threads.map { ChatThreadResponse.of(it) }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @DeleteMapping("/{threadId}")
    fun deleteThread(
        @AuthenticationPrincipal principal: CustomPrincipal,
        @PathVariable threadId: String
    ) {
        chatThreadService.deleteThread(principal.userId, threadId)
    }
}