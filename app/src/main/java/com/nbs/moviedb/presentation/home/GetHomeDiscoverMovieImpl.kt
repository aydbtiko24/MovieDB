package com.nbs.moviedb.presentation.home

import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants.FROM_INDEX
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_DISCOVER_SIZE
import com.nbs.moviedb.domain.usecase.movie.GetHomeDiscoverMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class GetHomeDiscoverMovieImpl(
    private val movieRepository: MovieRepository
) : GetHomeDiscoverMovies {

    override fun invoke(): Flow<List<Movie>> {
        return movieRepository.getDiscoverMovie()
            .map { it.subList(FROM_INDEX, HOME_DISCOVER_SIZE) }
    }
}
