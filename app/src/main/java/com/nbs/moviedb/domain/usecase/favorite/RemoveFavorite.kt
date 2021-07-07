package com.nbs.moviedb.domain.usecase.favorite

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface RemoveFavorite {

    suspend operator fun invoke(movieId: Long)
}