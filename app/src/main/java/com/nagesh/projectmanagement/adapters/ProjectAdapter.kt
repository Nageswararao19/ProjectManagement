package com.nagesh.projectmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nagesh.projectmanagement.R
import com.nagesh.projectmanagement.databinding.ItemCardProjectsBinding
import com.nagesh.projectmanagement.models.Project

class ProjectAdapter(
    private var list: List<Project>,
    private val onClick: (Project) -> Unit
) : RecyclerView.Adapter<ProjectAdapter.Holder>() {

    class Holder(val binding: ItemCardProjectsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCardProjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        (holder.binding).apply {
            tvProjectName.text = item.name
            tvDescription.text = item.description
            tvStartDate.text = item.startDate
            tvEndDate.text = item.endDate
            tvStatus.text = item.status

            // Simple visual logic based on color string
            val colorRes = when(item.status) {
                "Planned" -> R.color.status_blue_text
                "Completed" -> R.color.status_emerald_text
                else -> R.color.status_orange_text
            }
            val bgRes = when(item.status) {
                "Planned" -> R.color.status_blue_bg
                "Completed" -> R.color.status_emerald_bg
                else -> R.color.status_orange_bg
            }

            // Apply Status Colors
            tvStatus.setTextColor(ContextCompat.getColor(root.context, colorRes))
            statusContainer.setCardBackgroundColor(ContextCompat.getColor(root.context, bgRes))

            ivIcon.setColorFilter(ContextCompat.getColor(root.context, colorRes))
            ivContainer.setCardBackgroundColor(ContextCompat.getColor(root.context, bgRes))

            // when i click on view task button this will trigger...
            btnViewTasks.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<Project>) {
        list = newList
        notifyDataSetChanged()
    }
}