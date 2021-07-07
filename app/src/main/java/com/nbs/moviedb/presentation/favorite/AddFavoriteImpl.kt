package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.domain.repository.FavoriteRepository
import com.nbs.moviedb.domain.usecase.favorite.AddFavorite

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class AddFavoriteImpl(
    private val favoriteRepository: FavoriteRepository
) : AddFavorite {

    override suspend fun invoke(favorite: Favorite) {
        favoriteRepository.addFavorite(favorite)
    }
}
