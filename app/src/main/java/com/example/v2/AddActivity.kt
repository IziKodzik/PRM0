package com.example.v2

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.v2.databinding.ActivityAddBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    var adapterView: ArrayAdapter<String>? = null
    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var category = "Other"
    var date: LocalDate = LocalDate.now()
    var index = -1

    private val array = arrayOf(
        "Other",
        "Fun",
        "Food",
        "Education",
        "Hobby",
        "Relationship",
        "Investment",
        "Work"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinner = binding.spinner
        adapterView = ArrayAdapter(
            this,android.R.layout.simple_spinner_item,array
        ).also {
            arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
        spinner.onItemSelectedListener  = this


        date = LocalDate.now()
        val transfer = intent.getSerializableExtra("transfer") as? Transporter
        if (transfer != null) {
            fillData(transfer)
        }else {
            month += 1
            binding.textViewDate.text = Shared.formatDate(date)
        }
    }
    private fun fillData(transporter: Transporter){

        index = transporter.index
        with(transporter.transfer) {
            binding.spinner
            if(amount < 0)
                amount = - amount
            binding.textViewAmount.setText(amount.toString())
            binding.textViewDate.text = Shared.formatDate(date)
            binding.textViewTarget.setText(target)
            binding.checkBox.isChecked = incoming
            var position = array.indexOf(category)
            binding.spinner.setSelection(position)
        }
    }


    fun pickDate(view: View) {
        val dpd = DatePickerDialog(this, { _, year, month, day ->
            date = LocalDate.of(year, month, day)
            binding.textViewDate.text = Shared.formatDate(date)

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
                //co jest XDD?
                val transfer = Transfer(
                        amount ?: 0.0,
                         date,textViewTarget.text.toString() ?: "unknown",
                        category, checkBox.isChecked
                )
                if(index >= 0)
                    Shared.transferList.removeAt(index)
                else
                    index = Shared.transferList.size
                Shared.transferList.add(index, transfer)
                setResult(Activity.RESULT_OK)
                finish()
            }catch (e: NumberFormatException){
                return
            }

        }
    }

    fun onChange(view: View) {}


}