package com.nbs.moviedb.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteLocalDataSourceImplTest {

    private lateinit var database: AppDatabase
    private lateinit var favoriteLocalDataSource: FavoriteLocalDataSourceImpl
    private val favorites = favoriteEntities.asDomainModels()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = buildTestDb()
        favoriteLocalDataSource = FavoriteLocalDataSourceImpl(
            favoriteDao = database.favoriteDao()
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `add favorite, get favorites`() = runBlockingTest {
        // given
        favorites.forEach { favorite ->
            favoriteLocalDataSource.addFavorite(favorite)
        }
        // when
        val result = favoriteLocalDataSource.getFavorites().first()
        // then
        assertThat(result).containsExactlyElementsIn(favorites)
    }

    @Test
    fun `remove favorite, get favorites`() = runBlockingTest {
        // given
        val removedFavorite = favorites[1]
        favorites.forEach { favorite ->
            favoriteLocalDataSource.addFavorite(favorite)
        }
        // when
        favoriteLocalDataSource.removeFavorite(removedFavorite.movieId)
        // then
        val result = favoriteLocalDataSource.getFavorites().first()
        assertThat(result).doesNotContain(removedFavorite)
    }

    @Test
    fun `add favorite, get is favorite`() = runBlockingTest {
        // given
        favorites.forEach { favorite ->
            favoriteLocalDataSource.addFavorite(favorite)
        }
        // when
        val result = favoriteLocalDataSource.isFavorite(favoriteEntities[1].movieId).first()
        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `remove favorite, get is favorite`() = runBlockingTest {
        // given
        val removedFavorite = favorites[1]
        favorites.forEach { favorite ->
            favoriteLocalDataSource.addFavorite(favorite)
        }
        // when
        favoriteLocalDataSource.removeFavorite(removedFavorite.movieId)
        // then
        val result = favoriteLocalDataSource.isFavorite(removedFavorite.movieId).first()
        assertThat(result).isFalse()
    }
}
