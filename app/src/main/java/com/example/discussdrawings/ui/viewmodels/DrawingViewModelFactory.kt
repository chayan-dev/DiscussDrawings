package com.example.discussdrawings.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discussdrawings.repository.DrawingRepository

class DrawingViewModelFactory(
  private val drawingRepository: DrawingRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return DrawingViewModel(drawingRepository) as T
  }
}