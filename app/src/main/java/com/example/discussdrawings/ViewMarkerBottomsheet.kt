package com.example.discussdrawings

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discussdrawings.databinding.ViewMarkerBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ViewMarkerBottomsheet : BottomSheetDialogFragment(){

  lateinit var binding: ViewMarkerBottomsheetBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  lateinit var messageAdapter: MarkerMessageAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    messageAdapter = MarkerMessageAdapter() {  }
    binding = ViewMarkerBottomsheetBinding.inflate(inflater,container,false)
    binding.messageRv.layoutManager = LinearLayoutManager(context)
    binding.messageRv.adapter = messageAdapter
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.sendBtn.setOnClickListener {
      if (TextUtils.isEmpty(binding.nameEt.text)) {
        binding.nameEt.error = "message cannot be empty";
      } else {
        viewModel.updateMarker(binding.nameEt.text.toString())
        binding.nameEt.text?.clear()
        dismiss()
      }
    }

    viewModel.currentMarker.observe(viewLifecycleOwner){marker ->
      Log.d("view_marker",marker.toString())
      messageAdapter.submitList(marker.messages)
    }
  }
}