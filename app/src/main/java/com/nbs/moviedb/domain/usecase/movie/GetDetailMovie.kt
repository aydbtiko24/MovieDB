package com.nbs.moviedb.domain.usecase.movie

import com.nbs.moviedb.domain.models.DetailMovie
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface GetDetailMovie {

    operator fun invoke(movieId: Long): Flow<DetailMovie>
}
