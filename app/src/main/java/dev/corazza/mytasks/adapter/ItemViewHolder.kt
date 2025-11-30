package dev.corazza.mytasks.adapter

import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ListItemBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.listener.ClickListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ItemViewHolder(
  private val binding: ListItemBinding,
  private val listener: ClickListener
) : RecyclerView.ViewHolder(binding.root) {

  fun setData(task : Task) {
    binding.tvTitle.text = task.title

    val today = LocalDate.now()

    val colorResource = when {
      task.completed -> R.color.green
      task.date == null -> R.color.blue
      task.date.isBefore(today) -> R.color.red
      task.date.isEqual(today) -> R.color.yellow
      else -> R.color.blue
    }
    binding.tvTitle.setBackgroundResource(colorResource)

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
      val timeStr = task.time?.let { "$it" } ?: ""
      binding.tvDate.text = "$dateStr | $timeStr"
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