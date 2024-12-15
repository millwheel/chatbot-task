package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BaseTime
import jakarta.persistence.*

@Entity
class Chat constructor (
    @Column(columnDefinition = "TEXT")
    val question: String,

    @Column(columnDefinition = "TEXT")
    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    val chatThread: ChatThread,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTime() {


}