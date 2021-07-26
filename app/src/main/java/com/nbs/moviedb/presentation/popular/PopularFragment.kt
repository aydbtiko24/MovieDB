package com.nbs.moviedb.presentation.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.nbs.moviedb.databinding.ErrorViewBinding
import com.nbs.moviedb.databinding.FragmentPopularBinding
import com.nbs.moviedb.presentation.home.MovieItemClickListener
import com.nbs.moviedb.presentation.itemdecorations.GridItemDecoration
import com.nbs.moviedb.presentation.utils.ErrorState
import com.nbs.moviedb.presentation.utils.EventObserver
import com.nbs.moviedb.presentation.utils.FragmentViewDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class PopularFragment : FragmentViewDataBinding<FragmentPopularBinding>() {

    private val viewModel: PopularViewModel by viewModel()

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPopularBinding {
        return FragmentPopularBinding.inflate(inflater, container, false)
    }

    private lateinit var popularAdapter: PopularAdapter

    override fun initView() {
        popularAdapter = PopularAdapter(movieItemClickListener)
        binding.apply {
            lifecycleOwner = this@PopularFragment.viewLifecycleOwner
            swipeRefreshLayout.setOnRefreshListener { viewModel.getPopularData() }
            etSearchPopular.doAfterTextChanged { text ->
                viewModel.searchMovie(text.toString())
            }
            with(popularRecyclerView) {
                addItemDecoration(GridItemDecoration())
                adapter = popularAdapter
            }
            viewmodel = viewModel
        }
    }

    private val movieItemClickListener = object : MovieItemClickListener {
        override fun onClicked(movieId: Long) {
            val direction = PopularFragmentDirections.popularToDetailMovie(movieId)
            findNavController().navigate(direction)
        }
    }

    override fun observeData() {
        with(viewModel) {
            popularMovies.observe(viewLifecycleOwner) {
                popularAdapter.submitList(it)
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
        binding.etSearchPopular.isEnabled = !errorState.visible
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
        binding.popularRecyclerView.adapter = null
    }
}
