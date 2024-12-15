package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BaseTime
import jakarta.persistence.*

@Entity
@Table(name = "thread")
class ChatThread (
    val userId: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTime() {


}