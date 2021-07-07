package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.domain.repository.FavoriteRepository
import com.nbs.moviedb.domain.usecase.favorite.GetFavorites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class GetFavoritesImpl(
    private val favoriteRepository: FavoriteRepository
) : GetFavorites {

    override fun invoke(searchQuery: String): Flow<List<Favorite>> {
        return favoriteRepository.getFavorites()
            .map { favorites ->
                favorites.filter {
                    if (searchQuery.isEmpty()) return@filter true
                    it.title.lowercase().contains(searchQuery)
                }
            }
    }
}
