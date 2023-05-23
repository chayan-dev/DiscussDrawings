package com.example.discussdrawings

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discussdrawings.models.Drawing
import kotlinx.coroutines.launch

class DrawingViewModel(
  private val drawingRepository: DrawingRepository
) : ViewModel() {

  private val _allDrawingsList: MutableLiveData<ArrayList<Drawing>> = MutableLiveData()
  val allDrawingList: LiveData<ArrayList<Drawing>> = _allDrawingsList

  fun addDrawing(name:String,image: Uri) = viewModelScope.launch {
    drawingRepository.addDrawing(name,image){ handleAddDrawingLoader(it)}
  }

  fun getAllDrawings() = viewModelScope.launch {
    drawingRepository.getAllDrawings { drawingList ->
      _allDrawingsList.postValue(drawingList)
    }
  }

  private fun handleAddDrawingLoader(isSuccess: Boolean){

  }

}