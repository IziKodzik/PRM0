package com.example.v2

import java.io.Serializable
import java.time.LocalDate

data class Transfer(
    var amount: Double, var date: LocalDate, var target: String, var category: String,
    var incoming: Boolean) : Serializable{

    init{
        if(!incoming)
            amount = -amount
        if(target.isBlank())
            target="Target unknown"
    }

    override fun toString(): String {
        return "Transfer(amount=${amount}" +
                ", date=$date, target='$target', category='$category', incoming=$incoming)"
    }

}