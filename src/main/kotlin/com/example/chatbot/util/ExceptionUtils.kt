package com.example.chatbot.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <ID> fail(id: ID): Nothing {
    throw IllegalArgumentException("entity not found (id: $id)")
}

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: fail(id)
}