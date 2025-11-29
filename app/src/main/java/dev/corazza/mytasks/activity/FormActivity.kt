package dev.corazza.mytasks.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ActivityFormBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.extension.hasValue
import dev.corazza.mytasks.extension.textValue
import dev.corazza.mytasks.service.TaskService
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FormActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFormBinding
  private val taskService: TaskService by viewModels()
  private var taskId: Long? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityFormBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    intent.extras?.getSerializable("task")?.let { extra ->
      val task = extra as Task
      taskId = task.id
      binding.etTitle.setText(task.title)
      binding.etDescription.setText(task.description)
      binding.etDate.setText(task.formatDate())
      binding.etTime.setText(task.formatTime())
    }

    initComponents()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun initComponents() {
    binding.btSave.setOnClickListener {
      binding.layoutTitle.error = null

      if (binding.etTitle.text.isNullOrEmpty()) {
        binding.layoutTitle.error= ContextCompat.getString(this, R.string.title_required)
      } else {
        val date = if (binding.etDate.hasValue()) {
          LocalDate.parse(binding.etDate.textValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        } else null
        val time = if (binding.etTime.hasValue()) {
          LocalTime.parse(binding.etTime.textValue(), DateTimeFormatter.ofPattern("HH:mm"))
        } else null

        val task = Task(
          id = taskId,
          title = binding.etTitle.textValue(),
          description = binding.etDescription.textValue(),
          date = date,
          time = time
        )

        if (taskId == null) {
          taskService.create(task).observe(this) { response ->
            if (response.error) {
              showAlert(R.string.create_error)
            } else {
              finish()
            }
          }
        } else {
          taskService.update(task).observe(this) { response ->
            if (response.error) {
              showAlert(R.string.update_error)
            } else {
              finish()
            }

          }
        }
      }
    }
  }
  private fun showAlert(message: Int) {
    AlertDialog.Builder(this)
      .setMessage(message)
      .setNeutralButton(android.R.string.ok, null)
      .create()
      .show()
  }
}