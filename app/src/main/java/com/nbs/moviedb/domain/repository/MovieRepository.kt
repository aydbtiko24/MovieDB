package com.nbs.moviedb.domain.repository

import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie
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
    fun getDetailMovie(movieId: Long): Flow<DetailMovie>
    fun getMovieCast(movieId: Long): Flow<List<Cast>>
}
