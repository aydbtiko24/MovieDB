package com.nbs.moviedb.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.nbs.moviedb.databinding.ErrorViewBinding
import com.nbs.moviedb.databinding.FragmentHomeBinding
import com.nbs.moviedb.presentation.utils.ErrorState
import com.nbs.moviedb.presentation.utils.EventObserver
import com.nbs.moviedb.presentation.utils.FragmentViewDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class HomeFragment : FragmentViewDataBinding<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private lateinit var homeMovieAdapter: HomeMoviesAdapter

    override fun initView() {
        homeMovieAdapter = HomeMoviesAdapter(movieItemClickListener)
        binding.apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            swipeRefreshLayout.setOnRefreshListener { viewModel.getHomeData() }
            homeRecyclerView.adapter = homeMovieAdapter
            viewmodel = viewModel
        }
    }

    private val movieItemClickListener = object : MovieItemClickListener {
        override fun onClicked(movieId: Long) {
            val direction = HomeFragmentDirections.homeToDetailMovie(movieId)
            findNavController().navigate(direction)
        }
    }

    override fun observeData() {
        with(viewModel) {
            homeUiModels.observe(viewLifecycleOwner) {
                homeMovieAdapter.submitList(it)
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

    override fun cleanUpAdapter() {
        binding.homeRecyclerView.adapter = null
    }
}
