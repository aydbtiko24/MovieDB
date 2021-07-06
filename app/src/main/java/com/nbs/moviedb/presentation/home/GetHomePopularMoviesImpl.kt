package com.nbs.moviedb.presentation.home

import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants.FROM_INDEX
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_ITEMS_SIZE
import com.nbs.moviedb.domain.usecase.movie.GetHomePopularMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class GetHomePopularMoviesImpl(
    private val movieRepository: MovieRepository
) : GetHomePopularMovies {

    override fun invoke(): Flow<List<Movie>> {
        return movieRepository.getPopularMovie()
            .map { it.subList(FROM_INDEX, HOME_ITEMS_SIZE) }
    }
}
