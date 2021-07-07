package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.domain.repository.FavoriteRepository
import com.nbs.moviedb.domain.usecase.favorite.GetIsFavorite
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class GetIsFavoriteImpl(
    private val favoriteRepository: FavoriteRepository
) : GetIsFavorite {

    override fun invoke(movieId: Long): Flow<Boolean> {
        return favoriteRepository.isFavorite(movieId)
    }
}