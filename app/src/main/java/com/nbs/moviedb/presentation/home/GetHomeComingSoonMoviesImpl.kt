package com.nbs.moviedb.presentation.home

import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants.FROM_INDEX
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_ITEMS_SIZE
import com.nbs.moviedb.domain.usecase.movie.GetHomeComingSoonMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class GetHomeComingSoonMoviesImpl(
    private val movieRepository: MovieRepository
) : GetHomeComingSoonMovies {

    override fun invoke(year: String): Flow<List<Movie>> {
        return movieRepository.getComingSoonMovie(year)
            .map { it.subList(FROM_INDEX, HOME_ITEMS_SIZE) }
    }
}
