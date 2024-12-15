package com.example.chatbot.repository

import com.example.chatbot.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?

}