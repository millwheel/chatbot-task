package com.example.chatbot.service.user


import com.example.chatbot.config.security.component.JwtManager
import com.example.chatbot.dto.user.LoginRequest
import com.example.chatbot.dto.user.LoginSuccessResponse
import com.example.chatbot.dto.user.SignupRequest
import com.example.chatbot.dto.user.UserResponse
import com.example.chatbot.entity.user.User
import com.example.chatbot.entity.user.UserRole
import com.example.chatbot.exception.custom.InvalidEmailOrPasswordException
import com.example.chatbot.repository.UserRepository
import com.example.chatbot.util.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService (
    private val jwtManager: JwtManager,
    private val userRepository: UserRepository
){

    @Transactional
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

    fun login(loginRequest: LoginRequest) : LoginSuccessResponse {
        val user = userRepository.findByEmail(loginRequest.email) ?: throw InvalidEmailOrPasswordException()
        if (!matchesPassword(loginRequest.password, user.password)) {
            throw InvalidEmailOrPasswordException()
        }
        val token = jwtManager.generateToken(user.id, user.email, user.userRole.name)
        return LoginSuccessResponse(user.id, user.email, token)
    }

    fun getUserById(userId: String): User {
        return userRepository.findByIdOrThrow(userId)
    }

    fun getAllUsers(): List<UserResponse> {
        val users = userRepository.findAll()
        return users.map { UserResponse.of(it) }
    }

    @Transactional
    fun deleteUser(id: String) {
        val user = userRepository.findByIdOrThrow(id)
        userRepository.delete(user)
    }

}