package com.example.v2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.v2.databinding.ActivityMainBinding
import java.time.LocalDate
import kotlin.random.Random


const val REQUEST_ADD_TRANSFER = 1

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val transferAdapter by lazy { CustomAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecycler()
    }

    private fun setupRecycler() {

        for (i in 1..31) {
            Shared.transferList.add(
                Transfer(
                    Random.nextDouble(3.5, 385.3), LocalDate.of(2021, 3, i),
                    "Other", "Other", Random.nextBoolean()
                )
            )
        }


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            adapter = transferAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun openAdd(view: View) {
        startAddActivity(Intent(this, AddActivity::class.java))
    }

    fun startAddActivity(intent: Intent) {
        startActivityForResult(intent, REQUEST_ADD_TRANSFER)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_TRANSFER && resultCode == Activity.RESULT_OK)
            transferAdapter.refresh()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showMonthly(view: View) {
        startActivity(Intent(this, MonthlyActivity::class.java))
    }


}