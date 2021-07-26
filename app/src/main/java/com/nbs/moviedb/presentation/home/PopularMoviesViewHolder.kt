package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.PopularMoviesViewItemBinding
import com.nbs.moviedb.presentation.home.HomeUiModel.PopularMovies
import com.nbs.moviedb.presentation.itemdecorations.HorizontalItemDecoration

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class PopularMoviesViewHolder(
    private val binding: PopularMoviesViewItemBinding,
    movieItemClickListener: MovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val movieAdapter = MovieAdapter(movieItemClickListener)

    init {
        binding.popularRecyclerView.apply {
            addItemDecoration(HorizontalItemDecoration())
            adapter = movieAdapter
        }
    }

    fun bind(popular: PopularMovies) {
        binding.apply {
            movieAdapter.submitList(popular.items)
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            movieItemClickListener: MovieItemClickListener
        ): PopularMoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PopularMoviesViewItemBinding.inflate(inflater, parent, false)
            return PopularMoviesViewHolder(
                binding,
                movieItemClickListener
            )
        }
    }
}
