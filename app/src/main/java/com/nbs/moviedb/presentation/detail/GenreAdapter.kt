package com.nbs.moviedb.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.GenreViewItemBinding
import com.nbs.moviedb.domain.models.Genre

/**
 * Created by aydbtiko on 7/25/2021.
 *
 */
class GenreAdapter : ListAdapter<Genre, GenreAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: GenreViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Genre) {
            binding.apply {
                genre = item
                executePendingBindings()
            }
        }

        companion object {

            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = GenreViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
}