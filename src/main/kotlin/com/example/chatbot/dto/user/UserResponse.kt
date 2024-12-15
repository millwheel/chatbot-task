package com.example.chatbot.dto.user

import com.example.chatbot.entity.user.User
import java.time.OffsetDateTime

data class UserResponse (
    val id: String,
    val email: String,
    val name: String,
    val createdAt: OffsetDateTime
){
    companion object{
        fun of(user: User): UserResponse{
            return UserResponse(
                id = user.id,
                email = user.email,
                name = user.name,
                createdAt = user.createdAt
            )
        }
    }
}