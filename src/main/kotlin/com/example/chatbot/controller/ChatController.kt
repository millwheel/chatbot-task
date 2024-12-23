package com.example.chatbot.controller

import com.example.chatbot.dto.ResponseResult
import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.dto.chat.ChatResponse
import com.example.chatbot.dto.chat.ChatThreadResponse
import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.service.chat.ChatService
import com.example.chatbot.service.log.ChatLogService
import com.example.chatbot.service.log.UserActivityLogService
import com.example.chatbot.service.user.UserService
import org.springframework.data.domain.Page
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    fun getAllChats(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ) : ResponseResult<List<ChatThreadResponse>> {
        val chats = chatService.getAllChats(pageIndex, pageSize, orderDirection)
        val chatThreadResponses = groupChatsByThread(chats)
        return ResponseResult(chatThreadResponses)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/me")
    fun getMyThreads(
        @RequestAttribute userId: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ) : ResponseResult<List<ChatThreadResponse>> {
        val chats = chatService.getChatsByUser(userId, pageIndex, pageSize, orderDirection)
        val chatThreadResponses = groupChatsByThread(chats)
        return ResponseResult(chatThreadResponses)
    }

    private fun groupChatsByThread(chats: Page<Chat>) =
        chats
            .groupBy { it.chatThread.id }
            .map { (threadId, threadChats) ->
                ChatThreadResponse.fromChats(threadId, threadChats)
            }

}