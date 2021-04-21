package com.example.v2

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecycler()
    }

    private fun setupRecycler(){

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val transfers = ArrayList<Transfer>()
        transfers.add(Transfer(-2.0,"dupa", Calendar.getInstance().time))
        transfers.add(Transfer(12.0,"dupssa", Calendar.getInstance().time))
        transfers.add(Transfer(-52.0,"dupwqa", Calendar.getInstance().time))
        transfers.add(Transfer(62.0,"dagupa", Calendar.getInstance().time))
        transfers.add(Transfer(72.0,"tqweqwe",Calendar.getInstance().time))

        binding.recyclerView.apply{
            adapter = CustomAdapter(transfers)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
    }
}