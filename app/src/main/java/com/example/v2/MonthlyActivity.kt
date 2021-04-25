package com.example.v2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.v2.databinding.ActivityMonthlyBinding

class MonthlyActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMonthlyBinding.inflate(layoutInflater) }
    var isFragmentOneLoaded = true;
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun showGraph(view: View) {

        Shared.month = binding.editTextYear.text.toString().toInt()
        Shared.year = binding.editTextYear.text.toString().toInt()
        binding.view.invalidate()

    }
    suspend fun refreshGraph() = withContext(){


    }
}



