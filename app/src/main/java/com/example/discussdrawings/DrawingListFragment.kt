package com.example.discussdrawings

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discussdrawings.databinding.FragmentDrawingListBinding


class DrawingListFragment : Fragment() {

  private lateinit var binding: FragmentDrawingListBinding
  private val viewModel: DrawingViewModel by activityViewModels()
  private lateinit var drawingsAdapter: DrawingListAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    drawingsAdapter = DrawingListAdapter(){ openDrawing() }
    binding = FragmentDrawingListBinding.inflate(inflater, container, false)
    binding.drawingListRv.layoutManager = LinearLayoutManager(context)
    binding.drawingListRv.adapter = drawingsAdapter
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.getAllDrawings()

    viewModel.allDrawingList.observe(viewLifecycleOwner){ list ->
      list.forEach {
        Log.d("drawinglist", it.id)
      }
      drawingsAdapter.submitList(list)
    }

    binding.fab.setOnClickListener{
      findNavController().navigate(R.id.action_drawingListFragment_to_addDrawingFragment)
    }


  }

  private fun openDrawing() {
    findNavController().navigate(R.id.action_drawingListFragment_to_drawingFragment)
  }



}