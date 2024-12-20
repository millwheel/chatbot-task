package com.example.chatbot.exception.custom

class NotThreadOwnerException : RuntimeException(
   "You are not thread owner"
)