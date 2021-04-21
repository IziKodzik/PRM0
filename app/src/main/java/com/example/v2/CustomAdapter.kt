package com.example.v2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.databinding.ActivityMainBinding
import com.example.v2.databinding.ListLayoutBinding

class CustomAdapter(private val transferList: ArrayList<Transfer>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(transfer: Transfer){
            binding.textViewTarget.text = transfer.target
            binding.textViewValue.text = transfer.value.toString()
            if(transfer.value >=0)
                binding.textViewValue.setTextColor(Color.GREEN)
            else
                binding.textViewValue.setTextColor(Color.RED)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transferList[position])
    }

    override fun getItemCount(): Int = transferList.size

}
