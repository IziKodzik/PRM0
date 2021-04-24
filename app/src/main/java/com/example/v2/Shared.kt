package com.example.v2

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Shared {

    val transferList = mutableListOf<Transfer>()
    fun formatDate(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

}