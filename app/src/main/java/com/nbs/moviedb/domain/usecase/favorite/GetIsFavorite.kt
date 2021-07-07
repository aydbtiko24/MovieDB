package com.nbs.moviedb.domain.usecase.favorite

import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface GetIsFavorite {

    operator fun invoke(movieId: Long): Flow<Boolean>
}