package com.example.discussdrawings

import android.net.Uri
import com.example.discussdrawings.models.Drawing

class DrawingRepository {

  private val firebaseService = FirebaseService

  suspend fun addDrawing(name:String, image: Uri, callback:(isSuccess: Boolean) -> Unit) =
    firebaseService.addDrawing(name, image) { callback(it) }

  suspend fun getAllDrawings(callback:( drawingList: ArrayList<Drawing>) -> Unit) =
    firebaseService.getAllDrawings { callback(it) }

}