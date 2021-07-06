package com.nbs.moviedb.data.source.remote

import com.nbs.moviedb.data.source.remote.models.asDomainModel
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
}
