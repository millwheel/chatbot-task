package com.example.chatbot.entity.feedback

import com.example.chatbot.entity.BaseTime
import com.example.chatbot.entity.chat.Chat
import jakarta.persistence.*
import java.util.*

@Entity
// NOTE: 각 사용자는 대화 하나에 하나의 피드백만 생성 가능, 대화는 여러 피드백을 가질 수는 있음
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "chatId"])] )
class Feedback(
    @Column(nullable = false)
    val userId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    val chat: Chat,

    @Column(nullable = false)
    val isPositive: Boolean,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: FeedbackStatus = FeedbackStatus.PENDING,

    @Id
    val id: String = UUID.randomUUID().toString()

) : BaseTime() {

}