package com.nbs.moviedb.domain.repository

import com.nbs.moviedb.domain.models.Favorite
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface FavoriteRepository {

    suspend fun addFavorite(favorite: Favorite)
    fun getFavorites(): Flow<List<Favorite>>
    fun isFavorite(movieId: Long): Flow<Boolean>
    suspend fun removeFavorite(movieId: Long)
}
