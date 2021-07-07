package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.domain.repository.FavoriteRepository
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class RemoveFavoriteImpl(
    private val favoriteRepository: FavoriteRepository
) : RemoveFavorite {

    override suspend fun invoke(movieId: Long) {
        favoriteRepository.removeFavorite(movieId)
    }
}
