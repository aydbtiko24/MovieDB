package com.nbs.moviedb.domain.usecase.movie

import com.nbs.moviedb.domain.models.Cast
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface GetMovieCast {

    operator fun invoke(movieId: Long): Flow<List<Cast>>
}
