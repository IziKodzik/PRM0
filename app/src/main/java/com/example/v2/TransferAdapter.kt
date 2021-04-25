package com.example.v2

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.databinding.ListItemBinding


class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(transfer: Transfer) {
        with(binding) {
            textViewTarget.text = transfer.target
            textViewValue.text = transfer.amount.toString()
            if (transfer.amount >= 0)
                textViewValue.setTextColor(Color.GREEN)
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

class CustomAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<ViewHolder>() {

    var selectedItem = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding).also { holder ->
            binding.root.setOnClickListener { onItemClick(binding, parent, holder.layoutPosition) }
            binding.root.setOnLongClickListener { onLongItemClick(parent, holder.layoutPosition) }
        }

    }

    private fun onLongItemClick(parent: ViewGroup, index: Int): Boolean {
        val builder: AlertDialog.Builder = AlertDialog.Builder(parent.context)
        builder.setCancelable(true)
        builder.setTitle("Warning")
        builder.setMessage("Do you want to delete transfer?")
        builder.setPositiveButton(
            "DELETE"
        ) { i, w ->
            Shared.transferList.removeAt(index)

            refresh()
        }
        builder.setNegativeButton(android.R.string.cancel
        ) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener{dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)}
        dialog.show()
        return true
    }


    private fun onItemClick(binding: ListItemBinding, parent: ViewGroup, index: Int) {
        val intent = Intent(Intent(parent.context, AddActivity::class.java))
        intent.putExtra("transfer", Transporter(Shared.transferList[index], index))
        mainActivity.startAddActivity(intent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Shared.transferList[position])
    }

    override fun getItemCount(): Int = Shared.transferList.size

    fun refresh() {
        notifyDataSetChanged()
    }

}
