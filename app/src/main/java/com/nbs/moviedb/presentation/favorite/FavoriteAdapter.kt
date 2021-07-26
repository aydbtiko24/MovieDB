package com.nbs.moviedb.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.databinding.FavoriteViewItemBinding
import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.presentation.home.MovieItemClickListener

/**
 * Created by aydbtiko on 7/24/2021.
 *
 */
class FavoriteAdapter(
    private val movieItemClickListener: FavoriteMovieItemClickListener
) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent, movieItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: FavoriteViewItemBinding,
        private val movieItemClickListener: FavoriteMovieItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Favorite) {
            binding.apply {
                favorite = item
                root.setOnClickListener {
                    movieItemClickListener.onClicked(item.movieId)
                }
                btnUnfavorite.setOnClickListener {
                    movieItemClickListener.removeFavorite(item.movieId)
                }
                executePendingBindings()
            }
        }

        companion object {

            fun create(
                parent: ViewGroup,
                movieItemClickListener: FavoriteMovieItemClickListener
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, movieItemClickListener)
            }
        }
    }

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface FavoriteMovieItemClickListener : MovieItemClickListener {

    fun removeFavorite(movieId: Long)
}
