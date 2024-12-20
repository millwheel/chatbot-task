package com.example.chatbot.exception.custom

class DataNotFoundException(id: String?) : RuntimeException(
    "Data not found (id: $id)"
)