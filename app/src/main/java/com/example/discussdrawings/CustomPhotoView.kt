package com.example.discussdrawings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import com.github.chrisbanes.photoview.PhotoView

class ZoomableSpannableImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : PhotoView(context, attrs, defStyleAttr) {

  private lateinit var markerBitmap: Bitmap
  private var markerX = 0f
  private var markerY = 0f
  private lateinit var gestureDetector: GestureDetector

  init {
    setupGestureDetector()
  }

  private fun setupGestureDetector() {
    gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
      override fun onDoubleTap(e: MotionEvent): Boolean {
        sum()
        addMarker(e.x, e.y)
        return true
      }
    })
  }

  private fun sum() {
    // Perform your sum() function here
  }

  private fun addMarker(x: Float, y: Float) {
    val markerSize = resources.getDimension(R.dimen.marker_size)
    markerBitmap = Bitmap.createBitmap(markerSize.toInt(), markerSize.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(markerBitmap)
    val paint = Paint()
    paint.color = Color.RED
    paint.style = Paint.Style.FILL
    val radius = markerSize / 2
    canvas.drawCircle(radius, radius, radius, paint)

    markerX = x - markerSize / 2
    markerY = y - markerSize / 2

    invalidate()
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    gestureDetector.onTouchEvent(event)
    return super.onTouchEvent(event)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawBitmap(markerBitmap, markerX, markerY, null)
  }
}

