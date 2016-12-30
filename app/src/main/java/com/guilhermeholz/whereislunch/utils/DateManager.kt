package com.guilhermeholz.whereislunch.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@OpenForTesting
class DateManager {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")

    fun getCurrentDate(): String = formatter.format(LocalDate.now())
    fun isAfterMidDay(): Boolean = getCurrentTime().isAfter(LocalTime.NOON.plusMinutes(30))
    fun getCurrentTime(): LocalTime = LocalTime.now()
}