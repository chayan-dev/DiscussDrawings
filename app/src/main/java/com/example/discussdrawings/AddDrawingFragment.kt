package com.example.discussdrawings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.discussdrawings.databinding.FragmentAddDrawingBinding


class AddDrawingFragment : Fragment() {

  private lateinit var binding: FragmentAddDrawingBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  private lateinit var image: Uri
  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
      if (permission == true) {
        checkPermission()
      } else {
        Toast.makeText(context, "Permissions not granted", Toast.LENGTH_SHORT).show()
      }
    }
  private val resultGalleryLauncher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if(result.resultCode == Activity.RESULT_OK){
        val data = result.data?.data
        if(data != null){
          binding.drawing.setImageURI(data)
          image = data
//          image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, data))
//          } else {
//            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data)
//          }
        }
      }
    }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentAddDrawingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.addPhotoBtn.setOnClickListener { checkPermission() }

    binding.addDrawingBtn.setOnClickListener {
      if(TextUtils.isEmpty(binding.nameEt.text)) {
        binding.nameEt.error = "Name cannot be empty";
      }
      else{
        if(this::image.isInitialized){
          viewModel.addDrawing(
            binding.nameEt.text.toString(),
            image
          )
          findNavController().popBackStack()
        }
        else{
          Toast.makeText(requireContext(),"Choose Image to proceed", Toast.LENGTH_LONG).show()
        }
      }
    }
  }

  private fun checkPermission() {
    if (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // Permission is not granted, request it
      requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
      return
    }

    chooseImage()
  }

  private fun chooseImage() {
    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    resultGalleryLauncher.launch(pickPhoto)
  }

}