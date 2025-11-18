package dev.corazza.mytasks.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ActivityFormBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.service.TaskService

class FormActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFormBinding

  private lateinit var taskService: TaskService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityFormBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        val task = Task(title = binding.etTitle.text.toString())
        taskService.create(task)
        finish()
      }
    }
  }

}