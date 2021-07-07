package com.nbs.moviedb.presentation.home

import com.nbs.moviedb.domain.models.Movie

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
sealed class HomeUiModel {

    data class DiscoverMovies(val items: List<Movie>) : HomeUiModel()
    data class PopularMovies(val label: Int = 1, val items: List<Movie>) : HomeUiModel()
    data class ComingSoonMovies(val label: Int = 2, val items: List<Movie>) : HomeUiModel()
    data class Error(val message: String) : HomeUiModel()
}
