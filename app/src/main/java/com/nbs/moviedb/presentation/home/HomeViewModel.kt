package com.nbs.moviedb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbs.moviedb.domain.usecase.movie.GetHomeComingSoonMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomeDiscoverMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomePopularMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.ComingSoonMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.DiscoverMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.PopularMovies
import com.nbs.moviedb.presentation.utils.Event
import com.nbs.moviedb.presentation.utils.toYearQuery
import java.lang.System.currentTimeMillis
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class HomeViewModel(
    private val getHomeDiscoverMovies: GetHomeDiscoverMovies,
    private val getHomePopularMovies: GetHomePopularMovies,
    private val getHomeComingSoonMovies: GetHomeComingSoonMovies
) : ViewModel() {

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = _loadingData
    private val _loadingDataError = MutableLiveData<Event<String>>()
    val loadingDataError: LiveData<Event<String>> = _loadingDataError
    private val _homeUiModels = MutableLiveData<List<HomeUiModel>>()
    val homeUiModels: LiveData<List<HomeUiModel>> = _homeUiModels

    init {
        getHomeData()
    }

    private fun getHomeData() = viewModelScope.launch {
        combine(
            getHomeDiscoverMovies(),
            getHomePopularMovies(),
            getHomeComingSoonMovies(currentTimeMillis().toYearQuery()),
        ) { discoverMovies, popularMovies, comingSoonMovies ->
            val discover = DiscoverMovies(discoverMovies)
            val popular = PopularMovies(items = popularMovies)
            val comingSoon = ComingSoonMovies(items = comingSoonMovies)
            listOf(discover, popular, comingSoon)
        }
            .onStart { showLoadingData(visible = true) }
            .onCompletion { showLoadingData(visible = false) }
            .catch { cause -> handleDataError(cause) }
            .collect { homeUiModels -> _homeUiModels.value = homeUiModels }
    }

    private fun handleDataError(exception: Throwable) {
        val homeDataIsEmpty = _homeUiModels.value?.isEmpty() ?: true
        val errorMessage = exception.localizedMessage ?: ""

        if (homeDataIsEmpty) {
            _homeUiModels.value = listOf(HomeUiModel.Error(message = errorMessage))
        } else {
            _loadingDataError.value = Event(errorMessage)
        }
    }

    private fun showLoadingData(visible: Boolean) {
        _loadingData.value = visible
    }

    fun refreshData() {
        getHomeData()
    }

    fun retryGetData() {
        _homeUiModels.value = emptyList()
        refreshData()
    }
}
