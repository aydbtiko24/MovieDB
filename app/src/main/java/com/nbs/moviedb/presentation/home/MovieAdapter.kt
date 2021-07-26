package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.MovieViewItemBinding
import com.nbs.moviedb.domain.models.Movie

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class MovieAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent, movieItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: MovieViewItemBinding,
        private val movieItemClickListener: MovieItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.apply {
                movie = item
                root.setOnClickListener { movieItemClickListener.onClicked(item.id) }
                executePendingBindings()
            }
        }

        companion object {

            fun create(
                parent: ViewGroup,
                movieItemClickListener: MovieItemClickListener
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MovieViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, movieItemClickListener)
            }
        }
    }

    companion object {

        val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
