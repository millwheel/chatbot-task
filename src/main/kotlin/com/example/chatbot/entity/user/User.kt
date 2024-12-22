package com.example.chatbot.entity.user

import com.example.chatbot.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User constructor(
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var userRole: UserRole,

    @Id
    val id: String = UUID.randomUUID().toString(),
    @Column(nullable = false, unique = true)
    val email: String,
) : BasicTimeEntity() {

    init {
        if (this.email.isBlank()) {
            throw IllegalArgumentException("email cannot be blank")
        }
        if (this.password.isBlank()) {
            throw IllegalArgumentException("password cannot be blank")
        }
    }

}