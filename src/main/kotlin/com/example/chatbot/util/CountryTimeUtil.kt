package com.example.chatbot.util

import java.time.OffsetDateTime
import java.time.ZoneId

fun getStartAndEndTimeOfDayByCountryCode(country: String): Pair<OffsetDateTime, OffsetDateTime> {
    val zoneId = getZoneIdForCountry(country)
    val startTimeOfDay = OffsetDateTime.now().toLocalDate().atStartOfDay(zoneId).toOffsetDateTime()
    val endTimeOfDay = startTimeOfDay.plusDays(1).minusNanos(1)
    return Pair(startTimeOfDay, endTimeOfDay)
}

fun getZoneIdForCountry(country: String): ZoneId {
    return when (country.uppercase()) {
        "US" -> ZoneId.of("America/New_York")
        "KR" -> ZoneId.of("Asia/Seoul")
        "JP" -> ZoneId.of("Asia/Tokyo")
        "IN" -> ZoneId.of("Asia/Kolkata")
        else -> ZoneId.systemDefault()
    }
}