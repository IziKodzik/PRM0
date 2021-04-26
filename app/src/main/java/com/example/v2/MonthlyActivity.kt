package com.example.v2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.example.v2.databinding.ActivityMonthlyBinding
import java.lang.NumberFormatException
import java.time.DateTimeException
import java.time.LocalDate

class MonthlyActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMonthlyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editTextMonth.setText(Shared.graphDate.monthValue.toString())
        binding.editTextYear.setText(Shared.graphDate.year.toString())

    }

    fun showGraph(view: View) {
        refreshGraph()
    }

    private fun refreshGraph() {
       HandlerThread("th").also {

            it.run {
                try {
                    binding.editTextYear.setTextColor(Color.BLACK)
                    binding.editTextMonth.setTextColor(Color.BLACK)
                    val month = binding.editTextMonth.text.toString().toInt()
                    val year = binding.editTextYear.text.toString().toInt()
                    Shared.graphDate = LocalDate.of(year,month,1)
                    binding.view.invalidate()

                }catch (e: NumberFormatException){
                    binding.editTextYear.setTextColor(Color.RED)
                    binding.editTextMonth.setTextColor(Color.RED)
                    return
                }catch (e: DateTimeException){
                    binding.editTextYear.setTextColor(Color.RED)
                    binding.editTextMonth.setTextColor(Color.RED)
                    return
                }
            }
            it.start()
        }

    }
}



