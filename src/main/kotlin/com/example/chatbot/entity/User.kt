package com.example.chatbot.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val userRole: UserRole = UserRole.MEMBER
){

    init {
        if (this.email.isBlank()) {
            throw IllegalArgumentException("email cannot be blank")
        }
        if (this.password.isBlank()) {
            throw IllegalArgumentException("password cannot be blank")
        }
    }

}