package com.nbs.moviedb.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.domain.usecase.favorite.GetFavorites
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModel(
    private val getFavorites: GetFavorites,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: LiveData<String> = _searchQuery.asLiveData()
    val searchResultEnable: LiveData<Boolean> = searchQuery.map { it.isNotEmpty() }
    private val _favorites = _searchQuery
        .map { it.trim().lowercase() }
        .flatMapLatest { searchQuery -> getFavorites(searchQuery) }
    val favorites: LiveData<List<Favorite>> = _favorites.asLiveData()
    val dataIsEmpty: LiveData<Boolean> = combine(
        _searchQuery,
        _favorites
    ) { query, favorites ->
        query.isEmpty() && favorites.isEmpty()
    }.asLiveData()

    fun searchFavorite(query: String) {
        _searchQuery.value = query
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun remove(movieId: Long) = viewModelScope.launch {
        removeFavorite(movieId)
    }
}
