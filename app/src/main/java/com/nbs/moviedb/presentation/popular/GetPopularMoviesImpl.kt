package com.nbs.moviedb.presentation.popular

import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.GetPopularMovies
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class GetPopularMoviesImpl(
    private val movieRepository: MovieRepository
) : GetPopularMovies {

    override fun invoke(): Flow<List<Movie>> {
        return movieRepository.getPopularMovie()
    }
}