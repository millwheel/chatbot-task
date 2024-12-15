package com.example.chatbot.repository

import com.example.chatbot.entity.chat.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, String> {

}