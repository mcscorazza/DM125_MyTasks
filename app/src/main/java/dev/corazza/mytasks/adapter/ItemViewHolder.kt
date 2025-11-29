package dev.corazza.mytasks.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.listener.ClickListener

class ItemViewHolder(
  private val binding: ListItemBinding,
  private val listener: ClickListener
) : RecyclerView.ViewHolder(binding.root) {

  fun setData(task : Task) {
    binding.tvTitle.text = task.title

    if(task.completed) {
      binding.tvTitle.setBackgroundResource(R.color.green)
    } else {
      binding.tvTitle.setBackgroundResource(R.color.blue)
    }

    binding.tvDate.text = task.formatDateTime()

    binding.root.setOnClickListener {
      listener.onClick(task)
    }

    binding.root.setOnCreateContextMenuListener { menu, _, _ ->
      menu.add(R.string.mark_completed).setOnMenuItemClickListener {
        task.id?.let { id -> listener.onComplete(task.id) }
        true
      }
    }

  }
}