package com.nbs.moviedb.presentation.detail

import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants.ACTING_DEPARTMENT
import com.nbs.moviedb.domain.usecase.movie.GetMovieCast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class GetMovieCastImpl(
    private val movieRepository: MovieRepository
) : GetMovieCast {

    override fun invoke(movieId: Long): Flow<List<Cast>> {
        return movieRepository.getMovieCast(movieId).map {
            it.filter { cast -> cast.knownDepartment == ACTING_DEPARTMENT }
        }
    }
}
