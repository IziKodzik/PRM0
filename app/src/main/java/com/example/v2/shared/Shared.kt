package com.example.v2.shared

import com.example.v2.model.Transfer
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Shared {

    val transferList = mutableListOf<Transfer>()
    fun formatDate(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

    fun formatNumber(number: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }


}