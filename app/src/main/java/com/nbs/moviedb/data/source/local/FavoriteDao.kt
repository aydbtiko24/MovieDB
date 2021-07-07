package com.nbs.moviedb.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite ORDER BY _id DESC")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE movie_id=:movieId")
    fun isFavorite(movieId: Long): Flow<FavoriteEntity?>

    @Query("DELETE FROM favorite WHERE movie_id=:movieId")
    suspend fun removeFavorite(movieId: Long)
}
