package com.example.discussdrawings

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.discussdrawings.databinding.FragmentDrawingBinding
import com.example.discussdrawings.databinding.FragmentDrawingListBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.chrisbanes.photoview.PhotoView


class DrawingFragment : Fragment() {

  private lateinit var binding: FragmentDrawingBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  private lateinit var imageView: PhotoView
  private lateinit var markerBitmap: Bitmap
  private lateinit var imgBitmap: Bitmap
  private var markerX = 0f
  private var markerY = 0f
  private lateinit var gestureDetector: GestureDetectorCompat

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentDrawingBinding.inflate(inflater, container, false)
    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

//    val imageView = binding.drawingIv

    binding.drawingIv.setImageResource(R.drawable.sample_img)
    binding.drawingIv.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
      override fun onDoubleTap(event: MotionEvent): Boolean {
        // Handle double tap event here
        // Custom implementation goes here
        val x = event.x.toInt()  // get x-Coordinate
        val y = event.y.toInt()  // get y-Coordinate
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
          RelativeLayout.LayoutParams.WRAP_CONTENT) // Assuming you use a RelativeLayout
        val iv = ImageView(context)
        iv.setOnClickListener {

        }
        lp.setMargins(x, y, 0, 0) // set margins
        iv.layoutParams = lp
        iv.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.ic_push_pin_)) // set the image from drawable
        binding.outerLayout.addView(iv)
        return true
      }

      override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        return false
      }

      override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
      }
    })

//    binding.innerLayout.setOnTouchListener{
//        view, event ->
//      if (event.action == MotionEvent.ACTION_DOWN) {
//        val x = event.x.toInt()  // get x-Coordinate
//        val y = event.y.toInt()  // get y-Coordinate
//        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//          RelativeLayout.LayoutParams.WRAP_CONTENT) // Assuming you use a RelativeLayout
//        val iv = ImageView(context)
//        iv.setOnClickListener {
//
//        }
//        lp.setMargins(x, y, 0, 0) // set margins
//        iv.layoutParams = lp
//        iv.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.ic_push_pin_)) // set the image from drawable
//        (view as ViewGroup).addView(iv) // add a View programmatically to the ViewGroup
//      }
//      true
//    }

  }
}