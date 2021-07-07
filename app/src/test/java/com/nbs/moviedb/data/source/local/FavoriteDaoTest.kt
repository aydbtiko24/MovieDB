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
class FavoriteDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var favoriteDao: FavoriteDao

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = buildTestDb()
        favoriteDao = database.favoriteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `add favorite, get favorites`() = runBlockingTest {
        // given
        favoriteEntities.forEach { entity ->
            favoriteDao.addFavorite(entity)
        }
        // when
        val result = favoriteDao.getFavorites().first()
        // then
        assertThat(result).containsExactlyElementsIn(favoriteEntities)
    }

    @Test
    fun `remove favorite, get favorites`() = runBlockingTest {
        // given
        val removedFavorite = favoriteEntities[1]
        favoriteEntities.forEach { entity ->
            favoriteDao.addFavorite(entity)
        }
        // when
        favoriteDao.removeFavorite(removedFavorite.movieId)
        // then
        val result = favoriteDao.getFavorites().first()
        assertThat(result).doesNotContain(removedFavorite)
    }

    @Test
    fun `add favorite, get is favorite`() = runBlockingTest {
        // given
        favoriteEntities.forEach { entity ->
            favoriteDao.addFavorite(entity)
        }
        // when
        val result = favoriteDao.isFavorite(favoriteEntities[1].movieId).first()
        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `remove favorite, get is favorite`() = runBlockingTest {
        // given
        val removedFavorite = favoriteEntities[1]
        favoriteEntities.forEach { entity ->
            favoriteDao.addFavorite(entity)
        }
        // when
        favoriteDao.removeFavorite(removedFavorite.movieId)
        // then
        val result = favoriteDao.isFavorite(removedFavorite.movieId).first()
        assertThat(result).isNull()
    }
}
