package com.example.chatbot.config.security.component

import java.security.Principal

data class CustomPrincipal (
    val userId: String,
    val email: String,
) : Principal {
    override fun getName(): String = userId
}
