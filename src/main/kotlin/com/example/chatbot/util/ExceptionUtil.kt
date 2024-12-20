package com.example.chatbot.util

import com.example.chatbot.exception.custom.DataNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: throw DataNotFoundException(id.toString())
}