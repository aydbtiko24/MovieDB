package com.nbs.moviedb.data.source.local

import com.nbs.moviedb.domain.models.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class FavoriteLocalDataSourceImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteLocalDataSource {

    override suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.addFavorite(favorite.asEntity())
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getFavorites().map { it.asDomainModels() }
    }

    override fun isFavorite(movieId: Long): Flow<Boolean> {
        return favoriteDao.isFavorite(movieId).map { it != null }
    }

    override suspend fun removeFavorite(movieId: Long) {
        favoriteDao.removeFavorite(movieId)
    }
}
