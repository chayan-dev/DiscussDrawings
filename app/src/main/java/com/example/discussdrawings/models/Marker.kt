package com.example.discussdrawings.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Marker (
  val x_coordinate: String,
  val y_coordinate: String,
  val messages: List<String>,
  @ServerTimestamp
  val addition_time: Date? = null
){
  constructor() : this("","", listOf(),null)
}