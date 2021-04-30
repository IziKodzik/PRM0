package com.example.v2.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.view.View
import com.example.v2.databinding.ActivityMonthlyBinding
import java.lang.NumberFormatException
import java.time.DateTimeException
import java.time.LocalDate

class MonthlyActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMonthlyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var now = LocalDate.now()
        binding.editTextMonth.setText(now.monthValue.toString())
        binding.editTextYear.setText(now.year.toString())

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
                    binding.view.graphDate = LocalDate.of(year,month,1)
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



