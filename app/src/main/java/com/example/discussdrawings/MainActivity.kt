package com.example.discussdrawings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: DrawingViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val drawingRepository = DrawingRepository()
    viewModel =
      ViewModelProvider(this, DrawingViewModelFactory(drawingRepository))
        .get(DrawingViewModel::class.java)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
  }
}