package com.example.chatbot.entity.chat

import com.example.chatbot.entity.BaseTime
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "thread")
class ChatThread constructor (
    val userId: String,
    @OneToMany(mappedBy = "chatThread", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val chats: MutableList<Chat> = mutableListOf(),
    @Id
    val id: String = UUID.randomUUID().toString(),
) : BaseTime() {


}