package com.example.discussdrawings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DrawingViewModelFactory(
  private val drawingRepository: DrawingRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return DrawingViewModel(drawingRepository) as T
  }
}