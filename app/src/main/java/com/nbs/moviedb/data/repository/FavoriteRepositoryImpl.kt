package com.nbs.moviedb.data.repository

import com.nbs.moviedb.data.source.local.FavoriteLocalDataSource
import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override suspend fun addFavorite(favorite: Favorite) {
        favoriteLocalDataSource.addFavorite(favorite)
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoriteLocalDataSource.getFavorites()
    }

    override fun isFavorite(movieId: Long): Flow<Boolean> {
        return favoriteLocalDataSource.isFavorite(movieId)
    }

    override suspend fun removeFavorite(movieId: Long) {
        favoriteLocalDataSource.removeFavorite(movieId)
    }
}
