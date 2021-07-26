package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.ComingSoonMoviesViewItemBinding
import com.nbs.moviedb.presentation.home.HomeUiModel.ComingSoonMovies
import com.nbs.moviedb.presentation.itemdecorations.HorizontalItemDecoration

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class ComingSoonMoviesViewHolder(
    private val binding: ComingSoonMoviesViewItemBinding,
    movieItemClickListener: MovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val movieAdapter = MovieAdapter(movieItemClickListener)

    init {
        binding.comingSoonRecyclerView.apply {
            addItemDecoration(HorizontalItemDecoration())
            adapter = movieAdapter
        }
    }

    fun bind(comingSoon: ComingSoonMovies) {
        binding.apply {
            movieAdapter.submitList(comingSoon.items)
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            movieItemClickListener: MovieItemClickListener
        ): ComingSoonMoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ComingSoonMoviesViewItemBinding.inflate(inflater, parent, false)
            return ComingSoonMoviesViewHolder(
                binding,
                movieItemClickListener
            )
        }
    }
}
