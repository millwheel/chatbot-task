package com.example.chatbot.entity.log

import com.example.chatbot.entity.LogTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "chat_log")
class ChatLog constructor (
    @Column(nullable = false)
    val chatId: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    val question: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val email: String,
    @Id
    val id: String = UUID.randomUUID().toString()
): LogTimeEntity() {

}