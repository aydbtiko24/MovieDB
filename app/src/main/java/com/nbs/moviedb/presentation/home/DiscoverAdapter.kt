package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.DiscoverViewItemBinding
import com.nbs.moviedb.domain.models.Movie

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class DiscoverAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<Movie, DiscoverAdapter.ViewHolder>(MovieAdapter.COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent, movieItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class ViewHolder(
        private val binding: DiscoverViewItemBinding,
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
                val binding = DiscoverViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, movieItemClickListener)
            }
        }
    }
}
