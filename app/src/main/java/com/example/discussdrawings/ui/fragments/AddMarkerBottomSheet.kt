package com.example.discussdrawings.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.discussdrawings.ui.viewmodels.DrawingViewModel
import com.example.discussdrawings.databinding.AddMarkerBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMarkerBottomSheet : BottomSheetDialogFragment() {

  lateinit var binding: AddMarkerBottomsheetBinding
  private val viewModel: DrawingViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = AddMarkerBottomsheetBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.sendBtn.setOnClickListener {
      if (TextUtils.isEmpty(binding.nameEt.text)) {
        binding.nameEt.error = "message cannot be empty";
      } else {
        viewModel.addNewMarker(binding.nameEt.text.toString())
        dismiss()
      }
    }
  }
}