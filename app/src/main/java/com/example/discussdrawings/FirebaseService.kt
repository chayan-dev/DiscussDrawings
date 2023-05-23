package com.example.discussdrawings

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.discussdrawings.models.Drawing
import com.example.discussdrawings.models.Marker
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object FirebaseService {

  suspend fun addDrawing(
    name:String,
    image: Uri,
    callback:(isSuccess: Boolean) -> Unit
  ) {
    val storage = Firebase.storage
    val firestore= Firebase.firestore
    val storageRef = storage.getReference("images").child(System.currentTimeMillis().toString())

    val uploadTask = storageRef.putFile(image)
    val urlTask = uploadTask.continueWithTask { task ->
      if (!task.isSuccessful) {
        task.exception?.let {
          throw it
        }
      }
      storageRef.downloadUrl
    }.addOnCompleteListener { task ->
      if (task.isSuccessful) {
        val downloadUri = task.result
        firestore.collection("drawingTest").add(
          Drawing(
          id = "",
          name = name,
          imageUrl = downloadUri.toString(),
          markersList = listOf()
          )
        ).addOnSuccessListener { callback(true) }
         .addOnFailureListener { callback(false) }
      } else {
        // Handle failures
        // ...
      }
    }

  }

  suspend fun getAllDrawings(
    callback:( drawingList: ArrayList<Drawing>) -> Unit
  ){
    val db = Firebase.firestore
    val drawingList = arrayListOf<Drawing>()
    db.collection("drawingTest")
      .get()
      .addOnSuccessListener {  result ->
        for (document in result){
          drawingList.add(document.toObject(Drawing::class.java))
        }
        callback(drawingList)
      }
      .addOnFailureListener { e ->
        Log.d("getAllDrawings", "Error getting documents: ", e)
        callback(drawingList)
      }
  }

}