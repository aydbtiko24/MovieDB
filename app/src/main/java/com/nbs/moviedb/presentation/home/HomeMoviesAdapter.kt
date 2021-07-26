package com.nbs.moviedb.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.R
import com.nbs.moviedb.presentation.home.HomeUiModel.ComingSoonMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.DiscoverMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.PopularMovies

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class HomeMoviesAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<HomeUiModel, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DiscoverMovies -> R.layout.discover_movies_view_item
            is PopularMovies -> R.layout.popular_movies_view_item
            is ComingSoonMovies -> R.layout.coming_soon_movies_view_item
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.discover_movies_view_item -> DiscoverMoviesViewHolder.create(
                parent,
                movieItemClickListener
            )
            R.layout.popular_movies_view_item -> PopularMoviesViewHolder.create(
                parent,
                movieItemClickListener
            )
            R.layout.coming_soon_movies_view_item -> ComingSoonMoviesViewHolder.create(
                parent,
                movieItemClickListener
            )
            else -> throw UnsupportedOperationException("unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DiscoverMovies -> (holder as DiscoverMoviesViewHolder).bind(item)
            is PopularMovies -> (holder as PopularMoviesViewHolder).bind(item)
            is ComingSoonMovies -> (holder as ComingSoonMoviesViewHolder).bind(item)
        }
    }

    companion object {

        val COMPARATOR = object : DiffUtil.ItemCallback<HomeUiModel>() {
            override fun areItemsTheSame(oldItem: HomeUiModel, newItem: HomeUiModel): Boolean {
                return oldItem is DiscoverMovies && newItem is DiscoverMovies ||
                        oldItem is PopularMovies && newItem is PopularMovies ||
                        oldItem is ComingSoonMovies && newItem is ComingSoonMovies
            }

            override fun areContentsTheSame(oldItem: HomeUiModel, newItem: HomeUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
