package com.nbs.moviedb.data.repository

import com.nbs.moviedb.data.source.remote.MovieRemoteDataSource
import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getDiscoverMovie(): Flow<List<Movie>> = flow {
        emit(movieRemoteDataSource.getDiscoverMovie())
    }

    override fun getPopularMovie(): Flow<List<Movie>> = flow {
        emit(movieRemoteDataSource.getPopularMovie())
    }

    override fun getComingSoonMovie(year: String): Flow<List<Movie>> = flow {
        emit(movieRemoteDataSource.getComingSoonMovie(year))
    }

    override fun getDetailMovie(movieId: Long): Flow<DetailMovie> = flow {
        emit(movieRemoteDataSource.getDetailMovie(movieId))
    }

    override fun getMovieCast(movieId: Long): Flow<List<Cast>> = flow {
        emit(movieRemoteDataSource.getMovieCast(movieId))
    }
}
