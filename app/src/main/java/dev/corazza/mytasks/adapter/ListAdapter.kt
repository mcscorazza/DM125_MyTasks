package dev.corazza.mytasks.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task

class ListAdapter(
  private val context: Context,
  private val emptyMessage: TextView): RecyclerView.Adapter<ItemViewHolder>() {

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

  fun getItem(position: Int) = items[position]

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data: List<Task>) {
    items.clear()
    items.addAll(data)
    notifyDataSetChanged()
    checkEmptyList()
  }

  fun removeItem(position: Int){
    items.removeAt(position)
    notifyItemRemoved(position)
    checkEmptyList()
  }

  private fun checkEmptyList() {
    if (items.isEmpty()) {
      emptyMessage.visibility = View.VISIBLE
      emptyMessage.text = ContextCompat.getString(context, R.string.empty_list)
    } else {
      emptyMessage.visibility = View.INVISIBLE
    }
  }
}