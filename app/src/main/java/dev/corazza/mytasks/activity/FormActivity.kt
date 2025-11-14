package dev.corazza.mytasks.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dev.corazza.mytasks.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFormBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityFormBinding.inflate(layoutInflater)
    setContentView(binding.root)

  }
}