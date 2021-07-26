package com.nbs.moviedb.presentation.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.usecase.movie.GetPopularMovies
import com.nbs.moviedb.presentation.utils.ErrorState
import com.nbs.moviedb.presentation.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PopularViewModel(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = _loadingData
    private val _loadingDataError = MutableLiveData<Event<String>>()
    val loadingDataError: LiveData<Event<String>> = _loadingDataError
    private val _errorState = MutableLiveData(ErrorState.Initial)
    val errorState: LiveData<ErrorState> = _errorState
    private val _searchQuery = MutableStateFlow("")
    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies: LiveData<List<Movie>> = _searchQuery
        .map { it.trim().lowercase() }
        .flatMapLatest { query ->
            _popularMovies.map { movies ->
                movies.filter { movie ->
                    if (query.isEmpty()) return@filter true
                    movie.title.lowercase().contains(query)
                }
            }
        }.asLiveData()
    val searchQuery: LiveData<String> = _searchQuery.asLiveData()
    val searchResultEnable: LiveData<Boolean> = searchQuery.map { it.trim().isNotEmpty() }

    init {
        getPopularData()
    }

    fun getPopularData() = viewModelScope.launch {
        getPopularMovies()
            .onStart { showLoadingData(visible = true) }
            .onCompletion { showLoadingData(visible = false) }
            .catch { cause -> handleDataError(cause) }
            .collect { _popularMovies.value = it }
    }

    private fun handleDataError(exception: Throwable) {
        val errorMessage = exception.localizedMessage ?: ""
        if (_popularMovies.value.isEmpty()) {
            _errorState.value = ErrorState(
                message = errorMessage,
                visible = true
            )
        } else {
            _loadingDataError.value = Event(errorMessage)
        }
    }

    fun retryGetData() {
        _errorState.value = ErrorState.Initial
        getPopularData()
    }

    private fun showLoadingData(visible: Boolean) {
        _loadingData.value = visible
    }

    fun searchMovie(query: String) {
        _searchQuery.value = query
    }
}
