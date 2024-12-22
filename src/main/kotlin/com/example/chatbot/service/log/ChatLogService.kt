package com.example.chatbot.service.log

import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.entity.log.ChatLog
import com.example.chatbot.entity.user.User
import com.example.chatbot.repository.ChatLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatLogService (
    private val chatLogRepository: ChatLogRepository
){

    fun createChatLog(chat: Chat, user: User) {
        val chatLog = ChatLog(chat.id, chat.question, chat.answer, user.id, user.email)
        chatLogRepository.save(chatLog)
    }
}