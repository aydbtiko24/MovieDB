package com.nbs.moviedb.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
fun buildTestDb(): AppDatabase {
    return Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()
}

val favoriteEntities = listOf(
    FavoriteEntity(
        id = 1,
        movieId = 1,
        title = "Movie 1",
        backdropUrl = "url_1",
        yearDate = "2021",
        genres = "genre 1"
    ),
    FavoriteEntity(
        id = 2,
        movieId = 2,
        title = "Movie 2",
        backdropUrl = "url_2",
        yearDate = "2021",
        genres = "genre 2"
    ),
    FavoriteEntity(
        id = 3,
        movieId = 3,
        title = "Movie 3",
        backdropUrl = "url_3",
        yearDate = "2021",
        genres = "genre 3"
    ),
)