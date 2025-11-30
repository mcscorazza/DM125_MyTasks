package dev.corazza.mytasks.adapter

import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.listener.ClickListener
import java.time.format.DateTimeFormatter
import java.util.Locale

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

    val context = binding.root.context
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val isFullFormat = prefs.getBoolean("date_format", false)

    if (task.date != null) {
      val pattern = if (isFullFormat) {
        "dd 'de' MMMM 'de' yyyy"
      } else {
        "dd/MM/yyyy"
      }
      val locale = Locale.forLanguageTag("pt-BR")
      val formatter = DateTimeFormatter.ofPattern(pattern, locale)
      val dateStr = task.date.format(formatter)
      val timeStr = task.time?.let { " | $it" } ?: ""
      binding.tvDate.text = "$dateStr$timeStr"
    } else {
      binding.tvDate.text = ""
    }
    binding.root.setOnClickListener {
      listener.onClick(task)
    }

    binding.root.setOnCreateContextMenuListener { menu, _, _ ->
      menu.add(R.string.mark_completed).setOnMenuItemClickListener {
        task.id?.let { id -> listener.onComplete(id) }
        true
      }
    }

  }
}