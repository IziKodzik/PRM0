package com.example.v2

import android.text.Editable
import java.time.LocalDateTime
import java.util.*

data class Transfer(var amount: Double,var date: Date,var target: String, var category: String,
                    var incoming: Boolean){

    init{
        if(!incoming)
            amount = -amount
        if(target.isBlank())
            target="Target unknown"
    }


}