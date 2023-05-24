package com.example.discussdrawings.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Marker (
  val x_coordinate: String,
  val y_coordinate: String,
  var messages: List<String>,
  var addition_time: String? = null
){
  constructor() : this("","", listOf(),null)
}