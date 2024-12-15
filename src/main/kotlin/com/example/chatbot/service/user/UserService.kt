package com.example.chatbot.service.user

import com.example.chatbot.dto.user.LoginRequest
import com.example.chatbot.dto.user.SignupRequest
import com.example.chatbot.dto.user.UserResponse
import com.example.chatbot.entity.user.User
import com.example.chatbot.entity.user.UserRole
import com.example.chatbot.repository.UserRepository
import com.example.chatbot.util.*
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){

    fun createUser(signupRequest: SignupRequest): User {
        if (userRepository.existsByEmail(signupRequest.email)) {
            throw RuntimeException("Email already exists")
        }
        val user = User(
            email = signupRequest.email,
            password = encodePassword(signupRequest.password),
            name = signupRequest.name,
            userRole = signupRequest.role ?: UserRole.MEMBER
        )
        return userRepository.save(user)
    }

    fun login(loginRequest: LoginRequest) : String{
        val user = userRepository.findByEmail(loginRequest.email) ?: fail(loginRequest.email)
        if (!matchesPassword(loginRequest.password, user.password)) {
            throw RuntimeException("Invalid email or password")
        }
        val generateToken = generateToken(user.id, user.email, user.userRole.name)
        return generateToken
    }

    fun getAllUsers(): List<UserResponse> {
        val users = userRepository.findAll()
        return users.map { UserResponse.of(it) }
    }

    fun deleteUser(id: String) {
        val user = userRepository.findByIdOrThrow(id)
        userRepository.delete(user)
    }

}