package dev.corazza.mytasks.activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dev.corazza.mytasks.adapter.ListAdapter
import dev.corazza.mytasks.databinding.ActivityMainBinding
import dev.corazza.mytasks.entity.Task

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: ListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initComponents()
  }

  override fun onResume() {
    super.onResume()
    //adapter.addItem(Task(title="Task teste", date="16/11/2025"))
  }

  private fun initComponents() {
    adapter = ListAdapter()
    binding.rvMain.adapter = adapter

    binding.fabNew.setOnClickListener {
      startActivity(Intent(this, FormActivity::class.java))
    }
  }
}