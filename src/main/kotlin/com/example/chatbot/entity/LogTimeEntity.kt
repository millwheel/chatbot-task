package com.example.chatbot.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class LogTimeEntity (
    @CreatedDate
    @Column(updatable = false, name = "created_at")
    var createdAt: OffsetDateTime = OffsetDateTime.now()
)