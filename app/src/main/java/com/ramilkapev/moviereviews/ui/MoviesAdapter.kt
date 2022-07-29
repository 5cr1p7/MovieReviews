package com.ramilkapev.moviereviews.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ramilkapev.moviereviews.R
import com.ramilkapev.moviereviews.domain.models.Result
import javax.inject.Inject

class MoviesAdapter @Inject constructor() :
    PagingDataAdapter<Result, MoviesAdapter.ImagesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    inner class ImagesViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentItem: Result? = null
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        private var title: TextView = itemView.findViewById(R.id.txv_title)
        private var shortDesc: TextView = itemView.findViewById(R.id.txv_short_description)

        fun bind(item: Result?) = with(itemView) {
            currentItem = item
            Glide.with(this).load(item?.multimedia?.src).placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
            title.text = item?.displayTitle
            shortDesc.text = item?.summaryShort
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.summaryShort == newItem.summaryShort

    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}