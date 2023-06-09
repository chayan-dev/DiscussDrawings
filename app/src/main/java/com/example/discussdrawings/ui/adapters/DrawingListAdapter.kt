package com.example.discussdrawings.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.discussdrawings.R
import com.example.discussdrawings.databinding.DrawingListItemBinding
import com.example.discussdrawings.models.Drawing
import com.example.discussdrawings.utils.loadImage

class DrawingListAdapter(
  val onDrawingClicked: (drawing: Drawing) -> Unit
) : ListAdapter<Drawing, DrawingListAdapter.DrawingViewHolder>(
  object : DiffUtil.ItemCallback<Drawing>() {
    override fun areItemsTheSame(oldItem: Drawing, newItem: Drawing): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Drawing, newItem: Drawing): Boolean {
      return oldItem.toString() == newItem.toString()
    }
  }
) {
  inner class DrawingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): DrawingViewHolder {
    return DrawingViewHolder(
      parent.context.getSystemService(LayoutInflater::class.java).inflate(
        R.layout.drawing_list_item,
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {

    DrawingListItemBinding.bind(holder.itemView).apply {
      val drawing = getItem(position)

      thumbnail.loadImage(drawing.imageUrl)
      drawingName.text = drawing.name
      addedAtTv.text = drawing.addition_time?.toString()
      markersCount.text = drawing.markersList.size.toString()
      root.setOnClickListener { onDrawingClicked(drawing) }

    }
  }
}