package com.example.chatbot.service.chat

import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.repository.ChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ChatService (
    private val chatThreadService: ChatThreadService,
    private val chatRepository: ChatRepository
){

    @Transactional
    fun createChat(userId: String, question: String, answer: String): Chat {
        val chatThread = chatThreadService.getOrCreateThread(userId)

        val chat = Chat(
            question = question,
            answer = answer,
            chatThread = chatThread
        )

        chatThreadService.updateThreadTimestamp(chatThread)
        return chatRepository.save(chat)
    }

}