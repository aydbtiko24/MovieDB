package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.DiscoverMoviesViewItemBinding
import com.nbs.moviedb.presentation.home.HomeUiModel.DiscoverMovies
import com.nbs.moviedb.presentation.itemdecorations.IndicatorDecoration

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class DiscoverMoviesViewHolder(
    private val binding: DiscoverMoviesViewItemBinding,
    movieItemClickListener: MovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val snapHelper = LinearSnapHelper()
    private val discoverAdapter = DiscoverAdapter(movieItemClickListener)

    init {
        snapHelper.attachToRecyclerView(binding.discoverRecyclerView)
        binding.discoverRecyclerView.apply {
            adapter = discoverAdapter
            addItemDecoration(IndicatorDecoration())
        }
    }

    fun bind(discover: DiscoverMovies) {
        binding.apply {
            discoverAdapter.submitList(discover.items)
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            movieItemClickListener: MovieItemClickListener
        ): DiscoverMoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DiscoverMoviesViewItemBinding.inflate(inflater, parent, false)
            return DiscoverMoviesViewHolder(
                binding,
                movieItemClickListener
            )
        }
    }
}
