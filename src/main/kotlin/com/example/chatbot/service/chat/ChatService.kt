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
    fun createChat(userId: String, question: String): Chat {
        val chatThread = chatThreadService.getOrCreateThread(userId)

        // TODO API Sender 구현
        val chat = Chat(
            question = question,
            answer = "yes",
            chatThread = chatThread
        )

        chatThreadService.updateThreadTimestamp(chatThread)
        return chatRepository.save(chat)
    }

}