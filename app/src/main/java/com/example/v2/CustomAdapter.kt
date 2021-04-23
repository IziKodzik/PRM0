package com.example.v2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.databinding.ActivityMainBinding
import com.example.v2.databinding.ListItemBinding


class ViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(transfer: Transfer){
        with(binding){
            textViewTarget.text = transfer.target
            textViewValue.text = transfer.amount.toString()
            if(transfer.amount >=0)
                textViewValue.setTextColor(Color.GREEN)
            else
                textViewValue.setTextColor(Color.RED)
            textViewDate.text = transfer.date.toString()
            textViewCategory.text = transfer.category

        }

    }
}

class CustomAdapter() : RecyclerView.Adapter<ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Shared.transferList[position])
    }

    override fun getItemCount(): Int = Shared.transferList.size

    fun refresh(){
        notifyDataSetChanged()
    }

}
