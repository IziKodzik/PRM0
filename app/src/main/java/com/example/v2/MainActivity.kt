package com.example.v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.v2.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private val transferAdapter by lazy {CustomAdapter()}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecycler()
    }


    private fun setupRecycler(){

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply{
            adapter = transferAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    override fun onResume(){
        super.onResume()
        transferAdapter.refresh()
    }



    fun openAdd(view: View) {
        startActivity(Intent(this, AddActivity::class.java))
    }
}