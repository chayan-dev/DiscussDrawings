package com.example.discussdrawings

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.discussdrawings.databinding.FragmentDrawingBinding
import com.example.discussdrawings.models.Marker
import com.github.chrisbanes.photoview.PhotoView
import io.realworld.android.extensions.loadImage


class DrawingFragment : Fragment() {

  private lateinit var binding: FragmentDrawingBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  private lateinit var markerViews: ArrayList<View>

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

    markerViews = arrayListOf()

//    val imageView = binding.drawingIv
    viewModel.currentDrawing.observe(viewLifecycleOwner){drawing ->
      binding.drawingIv.loadImage(drawing.imageUrl)
      Log.d("DrawingFrag:",drawing.markersList.toString())
      drawing.markersList.forEach { showMarker(it) }
    }

    binding.drawingIv.setImageResource(R.drawable.sample_img)
    binding.drawingIv.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
      override fun onDoubleTap(event: MotionEvent): Boolean = doubleTapAction(event)

      override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        return false
      }

      override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
      }
    })

    binding.toggle.setOnCheckedChangeListener { button, isChecked ->
      if(isChecked){
        for(v in markerViews) v.visibility = View.VISIBLE
      }else {
        for(v in markerViews) v.visibility = View.INVISIBLE
      }
    }

  }

  private fun doubleTapAction(event: MotionEvent) : Boolean{
    Log.d("DrawingFragment", "double tap start")

    val x = event.x.toInt()  // get x-Coordinate
    val y = event.y.toInt()  // get y-Coordinate
//    val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//      RelativeLayout.LayoutParams.WRAP_CONTENT) // Assuming you use a RelativeLayout
//    val iv = ImageView(context)
//    iv.setOnClickListener {
//      //open bottom sheet to add marker
//    }
//    lp.setMargins(x, y, 0, 0) // set margins
//    iv.layoutParams = lp
//    iv.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.ic_push_pin_)) // set the image from drawable
//    binding.outerLayout.addView(iv)
    viewModel.setNewMarker(Marker(x.toString(),y.toString(), listOf()))
    AddMarkerBottomSheet().show(childFragmentManager,"AddMarkerBottomSheet")
    Log.d("DrawingFragment", "double tap end")
    return true
  }

  private fun showMarker(marker: Marker) {
    Log.d("DrawingFragment", "show marker start")
    val x = marker.x_coordinate.toInt()  // get x-Coordinate
    val y = marker.y_coordinate.toInt()  // get y-Coordinate
    val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT) // Assuming you use a RelativeLayout
    val iv = ImageView(context)
    iv.setOnClickListener {
      viewModel.setCurrentMarker(marker)
      ViewMarkerBottomsheet().show(childFragmentManager,"ViewMarkerBottomSheet")
    }
    lp.setMargins(x, y, 0, 0) // set margins
    iv.layoutParams = lp
    iv.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.ic_push_pin_)) // set the image from drawable
    binding.outerLayout.addView(iv)
    markerViews.add(iv)
    Log.d("DrawingFragment", "show marker end")
  }
}