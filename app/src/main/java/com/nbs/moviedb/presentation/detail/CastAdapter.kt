package com.nbs.moviedb.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.CastViewItemBinding
import com.nbs.moviedb.domain.models.Cast

/**
 * Created by aydbtiko on 7/25/2021.
 *
 */
class CastAdapter : ListAdapter<Cast, CastAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: CastViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cast) {
            binding.apply {
                cast = item
                executePendingBindings()
            }
        }

        companion object {

            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CastViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}