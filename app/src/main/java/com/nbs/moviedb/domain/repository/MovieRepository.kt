package com.nbs.moviedb.domain.repository

import com.nbs.moviedb.domain.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
interface MovieRepository {

    fun getDiscoverMovie(): Flow<List<Movie>>
    fun getPopularMovie(): Flow<List<Movie>>
    fun getComingSoonMovie(year: String): Flow<List<Movie>>
}
