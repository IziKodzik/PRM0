package com.example.v2

import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Shared {

    val transferList = mutableListOf<Transfer>()
    fun formatDate(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    var month = -1
    var year = -1
    fun formatNumber(number: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }


}