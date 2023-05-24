package com.example.discussdrawings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discussdrawings.models.Drawing
import com.example.discussdrawings.models.Marker
import kotlinx.coroutines.launch

class DrawingViewModel(
  private val drawingRepository: DrawingRepository
) : ViewModel() {

  private val _allDrawingsList: MutableLiveData<ArrayList<Drawing>> = MutableLiveData()
  val allDrawingList: LiveData<ArrayList<Drawing>> = _allDrawingsList

  private val _currentDrawing: MutableLiveData<Drawing> = MutableLiveData()
  val currentDrawing: LiveData<Drawing> = _currentDrawing

  private val _currentMarker: MutableLiveData<Marker> = MutableLiveData()
  val currentMarker: LiveData<Marker> = _currentMarker

  fun addDrawing(name:String,image: Uri) = viewModelScope.launch {
    drawingRepository.addDrawing(name,image){ handleAddDrawingLoader(it)}
  }

  fun getAllDrawings() = viewModelScope.launch {
    drawingRepository.getAllDrawings { drawingList ->
      _allDrawingsList.postValue(drawingList)
    }
  }

  fun setCurrentDrawing(drawing: Drawing) {
    _currentDrawing.value = drawing
  }

  fun setCurrentMarker(marker: Marker) {
    _currentMarker.value = marker
  }

  fun setNewMarker(marker: Marker) {
    _currentMarker.value = marker
  }

  private fun handleAddDrawingLoader(isSuccess: Boolean){

  }

  fun addNewMarker(text: String) {
    val tempMarker = _currentMarker.value?.messages?.plus(text)
    if (tempMarker != null) {
      _currentMarker.value?.messages = tempMarker.toList()
    }
    _currentMarker.value?.addition_time = System.currentTimeMillis().toString()

//    _allDrawingsList.value?.filter { it.id == _currentDrawing.value?.id }
//      ?.forEach { drawing ->
//        drawing.markersList.plus(_currentMarker)
//    }

    val tempDrawing = _currentDrawing.value?.markersList?.plus(_currentMarker.value)
    if(tempDrawing!=null){ _currentDrawing.value?.markersList = tempDrawing as List<Marker>
      _currentDrawing.value = _currentDrawing.value
    }

    Log.d("DrawingVM:",_currentMarker.value.toString())
    viewModelScope.launch {
      drawingRepository.addMarker(_currentMarker.value, _currentDrawing.value)
    }
  }
}