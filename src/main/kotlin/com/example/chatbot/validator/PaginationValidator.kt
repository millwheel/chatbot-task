package com.example.chatbot.validator

import org.springframework.stereotype.Component

@Component
class PaginationValidator {

    fun validateParameter(pageIndex: Int, pageSize: Int, orderDirection: String) {
        validatePageIndex(pageIndex, pageSize)
        validatePageSize(pageSize)
        validateOrderDirection(orderDirection)
    }

    private fun validatePageIndex(pageIndex: Int, pageSize: Int) {
        if (pageIndex < 0) {
            throw RuntimeException("page index can't be negative")
        }
    }

    private fun validatePageSize(pageSize: Int) {
        if (pageSize < 1) {
            throw RuntimeException("page size can't be less than 1")
        } else if (pageSize > 100) {
            throw RuntimeException("page size can't be more than 100")
        }
    }

    private fun validateOrderDirection(orderDirection: String) {
        if (orderDirection != "desc" && orderDirection != "asc") {
            throw RuntimeException("orderDirection should be either desc or asc")
        }
    }

}