package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BaseTime
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Chat constructor (
    val question: String,
    val answer: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTime() {


}