package com.example.chatbot.service

import com.example.chatbot.entity.User
import com.example.chatbot.entity.UserRole
import com.example.chatbot.repository.UserRepository
import com.example.chatbot.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){

    fun createUser(email: String, password: String, name: String, role: UserRole = UserRole.MEMBER): User {
        if (userRepository.existsByEmail(email)) {
            throw RuntimeException("Email already exists")
        }
        val user = User(
            email = email,
            password = password,
            name = name,
            userRole = role
        )
        return userRepository.save(user)
    }

    fun getUserById(id: String): User {
        return userRepository.findByIdOrThrow(id)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUser(id: String, name: String?, password: String?): User {
        val user = getUserById(id)
        if (!name.isNullOrBlank()) {
            user.name = name
        }
        if (!password.isNullOrBlank()) {
            user.password = password // 비밀번호는 암호화된 값을 저장해야 함
        }
        return userRepository.save(user)
    }

    fun deleteUser(id: String) {
        val user = getUserById(id)
        userRepository.delete(user)
    }

}