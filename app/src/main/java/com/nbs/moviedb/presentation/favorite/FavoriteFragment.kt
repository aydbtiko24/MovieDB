package com.nbs.moviedb.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.nbs.moviedb.R
import com.nbs.moviedb.databinding.ErrorViewBinding
import com.nbs.moviedb.databinding.FragmentFavoriteBinding
import com.nbs.moviedb.presentation.itemdecorations.ListItemDecoration
import com.nbs.moviedb.presentation.utils.FragmentViewDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by aydbtiko on 7/24/2021.
 *
 */
class FavoriteFragment : FragmentViewDataBinding<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun initView() {
        favoriteAdapter = FavoriteAdapter(movieItemClickListener)

        binding.apply {
            lifecycleOwner = this@FavoriteFragment.viewLifecycleOwner
            etSearchFavorite.doAfterTextChanged { text ->
                viewModel.searchFavorite(text.toString())
            }
            with(favoriteRecyclerView) {
                addItemDecoration(ListItemDecoration())
                adapter = favoriteAdapter
            }
            viewmodel = viewModel
        }
    }

    private val movieItemClickListener = object : FavoriteMovieItemClickListener {
        override fun onClicked(movieId: Long) {
            val direction = FavoriteFragmentDirections.favoriteToDetailMovie(movieId)
            findNavController().navigate(direction)
        }

        override fun removeFavorite(movieId: Long) {
            viewModel.remove(movieId)
        }
    }

    override fun observeData() {
        with(viewModel) {
            dataIsEmpty.observe(viewLifecycleOwner) { empty ->
                showEmptyView(empty)
            }
            favorites.observe(viewLifecycleOwner) {
                favoriteAdapter.submitList(it)
            }
        }
    }

    private fun showEmptyView(visible: Boolean) {
        binding.etSearchFavorite.isEnabled = !visible
        binding.emptyViewStub.viewStub?.isVisible = visible

        (binding.emptyViewStub.binding as? ErrorViewBinding)?.apply {
            container.isVisible = visible
            if (visible) {
                errorText = getString(R.string.empty_favorite_hint)
                btnRetry.isVisible = false
            }
        }
    }

    override fun cleanUpAdapter() {
        binding.favoriteRecyclerView.adapter = null
    }
}
