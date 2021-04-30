package com.example.v2.recycler

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.v2.shared.Shared
import com.example.v2.activity.AddActivity
import com.example.v2.activity.MainActivity
import com.example.v2.databinding.ListItemBinding
import com.example.v2.model.Transporter


class TransferAdapter(private val mainActivity: MainActivity) : RecyclerView.Adapter<ViewHolderTransfer>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTransfer {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderTransfer(binding).also { holder ->
            binding.root.setOnClickListener { onItemClick(parent, holder.layoutPosition) }
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
            mainActivity.refreshBalance()
            refresh()
        }
        builder.setNegativeButton(android.R.string.cancel
        ) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener{dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)}
        dialog.show()
        return true
    }

    private fun onItemClick(parent: ViewGroup, index: Int) {
        val intent = Intent(Intent(parent.context, AddActivity::class.java))
        intent.putExtra("transfer", Transporter(Shared.transferList[index], index))
        mainActivity.startAddActivity(intent)
    }

    override fun onBindViewHolder(holder: ViewHolderTransfer, position: Int) {
        holder.bind(Shared.transferList[position])
    }

    override fun getItemCount(): Int = Shared.transferList.size

    fun refresh() {
        notifyDataSetChanged()
    }

}
