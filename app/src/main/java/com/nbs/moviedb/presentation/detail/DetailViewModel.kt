package com.nbs.moviedb.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nbs.moviedb.data.source.local.asFavorite
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.usecase.favorite.AddFavorite
import com.nbs.moviedb.domain.usecase.favorite.GetIsFavorite
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite
import com.nbs.moviedb.domain.usecase.movie.GetDetailMovie
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
class DetailViewModel(
    movieId: Long,
    private val getDetailMovie: GetDetailMovie,
    private val getIsFavorite: GetIsFavorite,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = _loadingData
    private val _loadingDataError = MutableLiveData<Event<String>>()
    val loadingDataError: LiveData<Event<String>> = _loadingDataError
    private val _movieId = MutableStateFlow(movieId)
    private val _detailMovie = MutableLiveData<DetailMovie>()
    val detailMovie: LiveData<DetailMovie> = _detailMovie
    val favoriteUiState: LiveData<FavoriteUiState> = _movieId.flatMapLatest { movieId ->
        getIsFavorite(movieId).map { favorite ->
            FavoriteUiState(favorite)
        }
    }.asLiveData()

    init {
        getDetailMovieData()
    }

    fun getDetailMovieData() = viewModelScope.launch {
        getDetailMovie(_movieId.value)
            .onStart { showLoadingData(visible = true) }
            .onCompletion { showLoadingData(visible = false) }
            .catch { cause -> handleDataError(cause) }
            .collect { detailMovie -> _detailMovie.value = detailMovie }
    }

    private fun showLoadingData(visible: Boolean) {
        _loadingData.value = visible
    }

    private fun handleDataError(exception: Throwable) {
        val errorMessage = exception.localizedMessage ?: ""
        _loadingDataError.value = Event(errorMessage)
    }

    fun editFavorite() = viewModelScope.launch {
        val favorite = favoriteUiState.value?.favorite ?: false
        val movieId = _movieId.value
        if (favorite) {
            removeFavorite(movieId)
        } else {
            val detailMovie = detailMovie.value ?: return@launch
            addFavorite(detailMovie.asFavorite())
        }
    }
}
