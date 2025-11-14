package dev.corazza.mytasks.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task

class ItemViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

  fun setData(task : Task) {
    binding.tvTitle.text = task.title
    binding.tvDate.text = task.date
  }
}