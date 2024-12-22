package com.example.chatbot.entity.log

import com.example.chatbot.entity.LogTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user_activity_log")
class UserActivityLogEntity (

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val userActivityType: UserActivityType,

    @Id
    var id: String = UUID.randomUUID().toString()

) : LogTimeEntity() {

    companion object {
        fun ofSignUp(userId: String, email: String) : UserActivityLogEntity {
            return UserActivityLogEntity(
                userId = userId,
                email = email,
                userActivityType = UserActivityType.SIGNUP
            )
        }
        fun ofLogin(userId: String, email: String) : UserActivityLogEntity {
            return UserActivityLogEntity (
                userId = userId,
                email = email,
                userActivityType = UserActivityType.LOGIN
            )
        }
        fun ofChat(userId: String, email: String) : UserActivityLogEntity {
            return UserActivityLogEntity (
                userId = userId,
                email = email,
                userActivityType = UserActivityType.CHAT
            )
        }

    }
}