package com.example.chatbot.util

fun extractCountryFromHeader(acceptLanguage: String): String {
    return acceptLanguage.split(",").firstOrNull()?.split("-")?.getOrNull(1) ?: "KR"
}