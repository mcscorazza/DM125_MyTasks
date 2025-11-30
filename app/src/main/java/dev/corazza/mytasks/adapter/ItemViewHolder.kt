package dev.corazza.mytasks.adapter

import android.view.View
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
    binding.tvDescription.text = task.description

    val today = LocalDate.now()

    val colorResource = when {
      task.completed -> R.color.green
      task.date == null -> R.color.blue
      task.date.isBefore(today) -> R.color.red
      task.date.isEqual(today) -> R.color.yellow
      else -> R.color.blue
    }
    binding.viewStatusColor.setBackgroundResource(colorResource)

    val context = binding.root.context
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val isFullFormat = prefs.getBoolean("date_format", false)

    if (task.date != null) {
      val locale = Locale.forLanguageTag("pt-BR")
      val pattern = if (isFullFormat) "dd 'de' MMMM 'de' yyyy" else "dd/MM/yyyy"
      val formatter = DateTimeFormatter.ofPattern(pattern, locale)
      binding.tvDate.text = task.date.format(formatter)

      if (task.time != null) {
        binding.tvTime.text = task.time.toString()
        binding.tvTime.visibility = View.VISIBLE
      } else {
        binding.tvTime.text = ""
        binding.tvTime.visibility = View.INVISIBLE
      }
    } else {
      binding.tvDate.setText(R.string.no_date)
      binding.tvTime.visibility = View.INVISIBLE

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