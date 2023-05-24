package com.example.discussdrawings.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discussdrawings.ui.viewmodels.DrawingViewModel
import com.example.discussdrawings.R
import com.example.discussdrawings.databinding.FragmentDrawingListBinding
import com.example.discussdrawings.models.Drawing
import com.example.discussdrawings.ui.adapters.DrawingListAdapter


class DrawingListFragment : Fragment() {

  private lateinit var binding: FragmentDrawingListBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  private lateinit var drawingsAdapter: DrawingListAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    drawingsAdapter = DrawingListAdapter() { openDrawing(it) }
    binding = FragmentDrawingListBinding.inflate(inflater, container, false)
    binding.drawingListRv.layoutManager = LinearLayoutManager(context)
    binding.drawingListRv.adapter = drawingsAdapter
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.getAllDrawings()

    viewModel.allDrawingList.observe(viewLifecycleOwner) { list ->
      drawingsAdapter.submitList(list)
    }

    binding.fab.setOnClickListener {
      findNavController().navigate(R.id.action_drawingListFragment_to_addDrawingFragment)
    }

    binding.reloadBtn.setOnClickListener {
      viewModel.getAllDrawings()
      Toast.makeText(requireContext(), "Fetching data", Toast.LENGTH_SHORT).show()
    }
  }

  private fun openDrawing(drawing: Drawing) {
    viewModel.setCurrentDrawing(drawing)
    findNavController().navigate(R.id.action_drawingListFragment_to_drawingFragment)
  }

}