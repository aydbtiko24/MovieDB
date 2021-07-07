package com.nbs.moviedb.presentation.detail

import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.GetDetailMovie
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class GetDetailMovieImpl(
    private val movieRepository: MovieRepository
) : GetDetailMovie {

    override fun invoke(movieId: Long): Flow<DetailMovie> {
        return movieRepository.getDetailMovie(movieId)
    }
}
