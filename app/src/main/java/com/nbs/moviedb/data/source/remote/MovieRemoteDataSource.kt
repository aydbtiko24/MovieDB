package com.nbs.moviedb.data.source.remote

import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.models.Movie

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
interface MovieRemoteDataSource {

    suspend fun getDiscoverMovie(): List<Movie>
    suspend fun getPopularMovie(): List<Movie>
    suspend fun getComingSoonMovie(year: String): List<Movie>
    suspend fun getDetailMovie(movieId: Long): DetailMovie
    suspend fun getMovieCast(movieId: Long): List<Cast>
}
