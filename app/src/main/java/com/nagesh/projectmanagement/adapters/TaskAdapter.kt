package com.nagesh.projectmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nagesh.projectmanagement.R
import com.nagesh.projectmanagement.databinding.ItemCardTasksBinding
import com.nagesh.projectmanagement.models.Task


class TaskAdapter(private var list: List<Task>) : RecyclerView.Adapter<TaskAdapter.Holder>() {

    class Holder(val binding: ItemCardTasksBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCardTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            tvTaskTitle.text = item.title
            tvDescription.text = item.description
            tvAssignedTo.text = item.assignedTo
            tvDueDate.text = item.dueDate
            tvStatus.text = item.status

            val colorRes = when(item.status) {
                "Pending" -> R.color.status_blue_text
                "Done" -> R.color.status_emerald_text
                else -> R.color.status_orange_text
            }
            val bgRes = when(item.status) {
                "Pending" -> R.color.status_blue_bg
                "Done" -> R.color.status_emerald_bg
                else -> R.color.status_orange_bg
            }

            tvStatus.setTextColor(ContextCompat.getColor(root.context, colorRes))
            tvTaskStatusContainer.setCardBackgroundColor(ContextCompat.getColor(root.context, bgRes))

            ivTaskIcon.setColorFilter(ContextCompat.getColor(root.context, colorRes))
            ivTaskStatusContainer.setCardBackgroundColor(ContextCompat.getColor(root.context, bgRes))
        }
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<Task>) {
        list = newList
        notifyDataSetChanged()
    }
}