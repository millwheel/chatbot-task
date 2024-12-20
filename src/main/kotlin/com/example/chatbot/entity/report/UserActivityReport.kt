package com.example.chatbot.entity.report

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "user_activity_report")
data class UserActivityReport(

    @Column(nullable = false)
    var signUpCount: Int,

    @Column(nullable = false)
    var loginCount: Int,

    @Column(nullable = false)
    var chatCount: Int,

    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val date: OffsetDateTime,
)