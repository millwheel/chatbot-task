package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
class Chat constructor (
    @Column(nullable = false, columnDefinition = "TEXT")
    val question: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    val chatThread: ChatThread,

    @Id
    val id: String = UUID.randomUUID().toString(),
) : BasicTimeEntity() {


}