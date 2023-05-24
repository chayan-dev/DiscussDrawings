package com.example.discussdrawings.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.discussdrawings.R
import com.example.discussdrawings.databinding.ViewMarkerListItemBinding

class MarkerMessageAdapter(
  val onMarkerClicked: (message: String) -> Unit
) : ListAdapter<String, MarkerMessageAdapter.MarkerMessageViewHolder>(
  object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
      return oldItem.toString() == newItem.toString()
    }
  }
) {
  inner class MarkerMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MarkerMessageViewHolder {
    return MarkerMessageViewHolder(
      parent.context.getSystemService(LayoutInflater::class.java).inflate(
        R.layout.view_marker_list_item,
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MarkerMessageViewHolder, position: Int) {

    ViewMarkerListItemBinding.bind(holder.itemView).apply {
      val msg = getItem(position)

      message.text = msg
      root.setOnClickListener { onMarkerClicked(msg) }

    }
  }
}