package com.example.chatbot.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun createPageable(pageIndex: Int, pageSize: Int, orderDirection: String): Pageable {
    val sortDirection = Sort.Direction.fromString(orderDirection.lowercase())
    val sort = Sort.by(sortDirection, "createdAt")
    return PageRequest.of(pageIndex, pageSize, sort)
}