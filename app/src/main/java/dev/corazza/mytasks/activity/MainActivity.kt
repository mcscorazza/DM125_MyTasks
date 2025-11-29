package dev.corazza.mytasks.activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dev.corazza.mytasks.R
import dev.corazza.mytasks.adapter.ListAdapter
import dev.corazza.mytasks.adapter.TouchCallback
import dev.corazza.mytasks.databinding.ActivityMainBinding
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.listener.ClickListener
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

    askNotificationPermission()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
      R.id.preferences -> startActivity(Intent(this, PreferenceActivity::class.java))
      R.id.logout -> logout()
    }


    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()

    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val b = preferences.getBoolean("daily_notification", false)
    Log.e("pref", "Preference: $b")
    getTasks()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun initComponents() {
    binding.tvMessage.visibility = View.INVISIBLE
    adapter = ListAdapter(this, binding.tvMessage, object: ClickListener {
      override fun onComplete(id: Long) {
        taskService.complete(id).observe(this@MainActivity) { response ->
          if (!response.error) {
            getTasks()
          }
        }
      }

      override fun onClick(task: Task) {
        val intent = Intent(this@MainActivity, FormActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
      }

    })
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

    binding.srlMain.setOnRefreshListener {
      getTasks()
    }
  }

    private fun getTasks() {
    taskService.list().observe(this){ response ->
      binding.srlMain.isRefreshing = false

       response.value?.let {
          adapter.setData(it)
       } ?: run {
         binding.tvMessage.visibility = View.VISIBLE
         binding.tvMessage.text = ContextCompat.getString(this,R.string.empty_list)
        }
      }
    }

  private fun logout() {
    Firebase.auth.signOut()
    val intent = Intent(this, LoginActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
  }
  private fun askNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
        PackageManager.PERMISSION_GRANTED
      ) {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
    }
  }

  private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission(),
  ) { isGranted: Boolean ->
    if (!isGranted) {
      if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
        AlertDialog.Builder(this)
          .setTitle(R.string.permission)
          .setMessage(R.string.notification_permission_rationale)
          .setPositiveButton(android.R.string.ok, null)
          .setNegativeButton(android.R.string.cancel, null)
          .create()
          .show()
      }
    }
  }
}