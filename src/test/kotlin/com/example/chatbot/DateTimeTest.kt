package com.example.chatbot

import java.time.*
import kotlin.test.Test


class DateTimeTest {

    @Test
    fun testLocalDate() {
        val offsetNow = OffsetDateTime.now()
        val localNow = LocalDate.now()

        println(offsetNow)
        println(localNow)

        val toLocalDate = offsetNow.toLocalDate()
        println(toLocalDate)

        val offsetAsiaSeoul = toLocalDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toOffsetDateTime()
        val offsetAsiaNewYork = toLocalDate.atStartOfDay(ZoneId.of("America/New_York")).toOffsetDateTime()

        println(offsetAsiaSeoul)
        println(offsetAsiaNewYork)

        val localDateTimeNow = LocalDateTime.now()
        println(localDateTimeNow)
        val offsetDateTime = localDateTimeNow.atZone(ZoneId.of("Asia/Seoul")).toOffsetDateTime()
        println(offsetDateTime)
    }



}