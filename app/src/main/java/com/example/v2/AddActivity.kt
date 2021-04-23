package com.example.v2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.v2.databinding.ActivityAddBinding
import java.util.*

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var category = "Other"
    var income = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textViewDate.text = "${day}/${month + 1}/${year}"
        val spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.categoires,
            android.R.layout.simple_spinner_item)
            .also { adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        spinner.onItemSelectedListener  = this
    }

    fun pickDate(view: View) {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            this.year = year
            this.month = month + 1
            this.day = day
            binding.textViewDate.text = "${day}/${month}/${year}"

        }, year, month, day)

        dpd.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        category = "Other"
    }

    fun accept(view: View) {
        with(binding) {

            try {
                val amount = textViewAmount.text.toString().toDouble()
                val transfer = Transfer(
                        textViewAmount.text.toString().toDouble() ?: 0.0,
                         Date(day, month, year),textViewTarget.text.toString() ?: "unknown",
                        category, income
                )
                Shared.transferList.add(transfer)
                binding.textViewDebug.text = Shared.transferList.toString()
                finish()
            }catch (e: NumberFormatException){
                return
            }

        }



    }

    fun boxChanged(view: View) {
        income = !income
    }


}