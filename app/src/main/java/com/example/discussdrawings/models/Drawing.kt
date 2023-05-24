package com.example.discussdrawings.models

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Drawing(
  val name: String,
  @DocumentId
  val id: String,
  @ServerTimestamp
  val addition_time: Date? = null,
  val imageUrl: String,
  var markersList: List<Marker>
){
  constructor() : this("","",null,"", listOf())
}
