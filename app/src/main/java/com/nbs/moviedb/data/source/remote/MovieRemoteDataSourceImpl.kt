package com.nbs.moviedb.data.source.remote

import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.models.Movie

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class MovieRemoteDataSourceImpl(
    private val apiService: ApiService
) : MovieRemoteDataSource {

    override suspend fun getDiscoverMovie(): List<Movie> {
        return apiService.getDiscoverMovies().results.map {
            it.asDomainModel()
        }
    }

    override suspend fun getPopularMovie(): List<Movie> {
        return apiService.getPopularMovies().results.map {
            it.asDomainModel()
        }
    }

    override suspend fun getComingSoonMovie(year: String): List<Movie> {
        return apiService.getComingSoonMovies(year = year).results.map {
            it.asDomainModel()
        }
    }

    override suspend fun getDetailMovie(movieId: Long): DetailMovie {
        return apiService.getDetailMovie(movieId = movieId).asDomainModel()
    }

    override suspend fun getMovieCast(movieId: Long): List<Cast> {
        return apiService.getMovieCast(movieId = movieId).casts.asDomainModels()
    }
}
