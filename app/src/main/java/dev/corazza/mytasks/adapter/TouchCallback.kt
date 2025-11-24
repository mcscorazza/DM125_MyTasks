package dev.corazza.mytasks.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.listener.SwipeListener

class TouchCallback(private val listener: SwipeListener) : ItemTouchHelper.Callback() {
  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder
  ): Int {
    return makeMovementFlags(
      0,
      ItemTouchHelper.START or ItemTouchHelper.END
    )
  }

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean {
    return false
  }

  override fun onSwiped(
    viewHolder: RecyclerView.ViewHolder,
    direction: Int
  ) {
    listener.onSwipe(viewHolder.absoluteAdapterPosition)
  }

}