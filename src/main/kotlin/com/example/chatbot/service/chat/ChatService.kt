package com.example.chatbot.service.chat

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.repository.ChatRepository
import com.example.chatbot.sender.OpenaiApiSender
import com.example.chatbot.util.createPageable
import com.example.chatbot.util.parseAnswerFromResponse
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ChatService (
    private val chatThreadService: ChatThreadService,
    private val chatRepository: ChatRepository,
    private val openaiApiSender: OpenaiApiSender
){

    @Transactional
    fun createChat(userId: String, model: String, isStreaming: Boolean, chatRequest: ChatRequest): Chat {
        val chatThread = chatThreadService.getOrCreateThread(userId)

        val question = chatRequest.question
        val result = openaiApiSender.sendRequestAndGetResponse(question, model)
        val answer = parseAnswerFromResponse(result)

        val chat = Chat(
            question = question,
            answer = answer,
            chatThread = chatThread
        )

        chatThreadService.updateThreadTimestamp(chatThread)
        return chatRepository.save(chat)
    }

    fun getAllChats(pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findAll(pageable)
    }

    fun getChatsByUser(userId: String, pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findByUserId(userId, pageable)
    }

}