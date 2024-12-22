package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "thread")
class ChatThread constructor (
    @Column(nullable = false)
    val userId: String,
    @OneToMany(mappedBy = "chatThread", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val chats: MutableList<Chat> = mutableListOf(),
    @Id
    val id: String = UUID.randomUUID().toString()
) : BasicTimeEntity() {


}