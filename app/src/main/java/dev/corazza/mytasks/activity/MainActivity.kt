package dev.corazza.mytasks.activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import dev.corazza.mytasks.R
import dev.corazza.mytasks.adapter.ListAdapter
import dev.corazza.mytasks.adapter.TouchCallback
import dev.corazza.mytasks.databinding.ActivityMainBinding
import dev.corazza.mytasks.listener.SwipeListener
import dev.corazza.mytasks.service.TaskService

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: ListAdapter
  private val taskService : TaskService by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initComponents()
  }

  override fun onResume() {
    super.onResume()
    getTasks()
  }

  private fun initComponents() {
    binding.tvMessage.visibility = View.INVISIBLE
    adapter = ListAdapter(this, binding.tvMessage)
    binding.rvMain.adapter = adapter

    binding.fabNew.setOnClickListener {
      startActivity(Intent(this, FormActivity::class.java))
    }

    ItemTouchHelper(TouchCallback(object: SwipeListener {
      override fun onSwipe(position: Int) {
        adapter.getItem(position).id?.let {
          taskService.delete(it).observe(this@MainActivity){ response ->
            if(response.error) {
              adapter.notifyItemChanged(position)
            } else {
              adapter.removeItem(position)
            }
          }
        }
      }

    })).attachToRecyclerView(binding.rvMain)
  }

    private fun getTasks() {
    taskService.list().observe(this){ response ->
       response.value?.let {
          adapter.setData(it)
       } ?: run {
         binding.tvMessage.visibility = View.VISIBLE
         binding.tvMessage.text = ContextCompat.getString(this,R.string.empty_list)
        }
      }
    }
}