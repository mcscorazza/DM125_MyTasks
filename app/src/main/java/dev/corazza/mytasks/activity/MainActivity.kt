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
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initComponents()
  }

  private fun initComponents() {
    val adapter = ListAdapter()
    binding.rvMain.adapter = adapter
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))
    adapter.addItem(Task(title="Primeira Task", date = "14/11/2025"))

    binding.fabNew.setOnClickListener {
      startActivity(Intent(this, FormActivity::class.java))
    }
  }
}