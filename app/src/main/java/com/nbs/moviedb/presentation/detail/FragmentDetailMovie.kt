package com.nbs.moviedb.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.nbs.moviedb.R
import com.nbs.moviedb.databinding.ErrorViewBinding
import com.nbs.moviedb.databinding.FragmentDetailMovieBinding
import com.nbs.moviedb.presentation.itemdecorations.GenreItemDecoration
import com.nbs.moviedb.presentation.itemdecorations.HorizontalItemDecoration
import com.nbs.moviedb.presentation.main.MainViewModel
import com.nbs.moviedb.presentation.utils.ErrorState
import com.nbs.moviedb.presentation.utils.EventObserver
import com.nbs.moviedb.presentation.utils.FragmentViewDataBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by aydbtiko on 7/24/2021.
 *
 */
class FragmentDetailMovie : FragmentViewDataBinding<FragmentDetailMovieBinding>() {

    private val args: FragmentDetailMovieArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(args.movieId)
    }
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailMovieBinding {
        setStatusBarColor(android.R.color.transparent)
        return FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
    }

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var castAdapter: CastAdapter

    override fun initView() {
        mainViewModel.togglesBottomNavVisibility(visible = false)
        // custom back navigation
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateUp()
        }

        genreAdapter = GenreAdapter()
        castAdapter = CastAdapter()

        binding.apply {
            lifecycleOwner = this@FragmentDetailMovie.viewLifecycleOwner
            toolbar.setNavigationOnClickListener { navigateUp() }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getDetailMovieData()
            }
            with(genresRecyclerView) {
                ContextCompat.getDrawable(requireContext(), R.drawable.genre_divider)?.let {
                    addItemDecoration(GenreItemDecoration(it))
                }
                adapter = genreAdapter
            }
            with(castRecyclerView){
                addItemDecoration(HorizontalItemDecoration())
                adapter = castAdapter
            }
            viewmodel = viewModel
        }
    }

    override fun observeData() {
        with(viewModel) {
            detailMovie.observe(viewLifecycleOwner) { detailMovie ->
                genreAdapter.submitList(detailMovie.genres)
            }

            movieCasts.observe(viewLifecycleOwner){ casts ->
                castAdapter.submitList(casts)
            }

            loadingData.observe(viewLifecycleOwner) { dataLoading ->
                binding.headerViews.isInvisible = dataLoading
            }

            loadingDataError.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })

            errorState.observe(viewLifecycleOwner) { errorState ->
                setErrorView(errorState)
            }
        }
    }

    private fun setErrorView(errorState: ErrorState) {
        binding.headerViews.isInvisible = errorState.visible
        binding.swipeRefreshLayout.isEnabled = !errorState.visible
        binding.errorViewStub.viewStub?.isVisible = errorState.visible
        (binding.errorViewStub.binding as? ErrorViewBinding)?.apply {
            container.isVisible = errorState.visible
            errorText = errorState.message
            retryClickListener = View.OnClickListener {
                viewModel.retryGetData()
            }
        }
    }

    private fun navigateUp() {
        mainViewModel.togglesBottomNavVisibility(visible = true)
        setStatusBarColor(R.color.primary)
        findNavController().navigateUp()
    }

    private fun setStatusBarColor(@ColorRes colorRes: Int) {
        requireActivity().window.statusBarColor = ContextCompat.getColor(
            requireContext(),
            colorRes
        )
    }

    override fun cleanUpAdapter() {
        binding.apply {
            genresRecyclerView.adapter = null
            castRecyclerView.adapter = null
        }
    }
}

/** Binding adapter to state favorite button's state */
@BindingAdapter("favoriteState")
fun MaterialButton.setFavoriteState(favoriteUiState: FavoriteUiState?) {
    favoriteUiState?.let {
        this.text = this.resources.getString(it.textResId)
        this.icon = ContextCompat.getDrawable(this.context, favoriteUiState.iconResId)
    }
}