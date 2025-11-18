package dev.corazza.mytasks.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task

class ListAdapter(): RecyclerView.Adapter<ItemViewHolder>() {

  private val items = mutableListOf<Task>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ItemViewHolder {
    val binding = ListItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return ItemViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: ItemViewHolder,
    position: Int
  ) {
    holder.setData(items[position])
  }

  override fun getItemCount() = items.size

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data: List<Task>) {
    items.clear()
    items.addAll(data)
    notifyDataSetChanged()
  }

  fun addItem(item: Task) {
    items.add(item)
    notifyItemInserted(items.size - 1)
  }
}