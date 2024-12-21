package com.example.chatbot.entity.log

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "user_activity_log")
class UserActivityLog (

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val userActivityType: UserActivityType,

    @Column(nullable = false)
    val timestamp: OffsetDateTime = OffsetDateTime.now(),

    @Id
    var id: String = UUID.randomUUID().toString()
){

}