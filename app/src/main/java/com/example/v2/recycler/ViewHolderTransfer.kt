package com.example.v2.recycler

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.R
import com.example.v2.shared.Shared
import com.example.v2.databinding.ListItemBinding
import com.example.v2.model.Transfer


class ViewHolderTransfer(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(transfer: Transfer) {
        with(binding) {
            textViewTarget.text = transfer.target
            textViewValue.text = Shared.formatNumber(transfer.amount)
            if (transfer.amount >= 0)
                textViewValue.setTextColor(Color.parseColor("#0dbf49"))
            else
                textViewValue.setTextColor(Color.RED)
            textViewDate.text = Shared.formatDate(transfer.date)
            textViewCategory.text = transfer.category
            var image = R.drawable.ic_other
            when (transfer.category) {
                "Education" -> image = R.drawable.ic_education
                "Food" -> image = R.drawable.ic_food
                "Fun" -> image = R.drawable.ic_fun
                "Hobby" -> image = R.drawable.ic_hobby
                "Investment" -> image = R.drawable.ic_investment
                "Other" -> image = R.drawable.ic_other
                "Relationship" -> image = R.drawable.ic_relationship
                "Work" -> image = R.drawable.ic_work
            }
            imageView.setBackgroundResource(image)

        }

    }


}