package com.nbs.moviedb.data.repository

import com.nbs.moviedb.data.source.local.FavoriteLocalDataSource
import com.nbs.moviedb.data.source.local.asDomainModels
import com.nbs.moviedb.data.source.local.favoriteEntities
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@ExperimentalCoroutinesApi
class FavoriteRepositoryImplTest {

    private lateinit var favoriteRepository: FavoriteRepositoryImpl

    @MockK
    lateinit var favoriteLocalDataSource: FavoriteLocalDataSource
    private val favorites = favoriteEntities.asDomainModels()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favoriteRepository = FavoriteRepositoryImpl(
            favoriteLocalDataSource = favoriteLocalDataSource
        )
    }

    @Test
    fun `add favorite`() = runBlockingTest {
        // given
        val favorite = favorites[1]
        coEvery { favoriteLocalDataSource.addFavorite(favorite) } coAnswers {
            println("Inserted to data source")
        }
        // when
        favoriteRepository.addFavorite(favorite)
        // then
        coVerify { favoriteLocalDataSource.addFavorite(favorite) }
    }

    @Test
    fun `get favorites`() = runBlockingTest {
        // given
        every { favoriteLocalDataSource.getFavorites() } returns flowOf(favorites)
        // when
        favoriteRepository.getFavorites().first()
        // then
        coVerify { favoriteLocalDataSource.getFavorites() }
    }

    @Test
    fun `get is favorites`() = runBlockingTest {
        // given
        val movieId = favorites[2].movieId
        every { favoriteLocalDataSource.isFavorite(movieId) } returns flowOf(true)
        // when
        favoriteRepository.isFavorite(movieId).first()
        // then
        coVerify { favoriteLocalDataSource.isFavorite(movieId) }
    }

    @Test
    fun `remove favorites`() = runBlockingTest {
        // given
        val movieId = favorites[1].movieId
        coEvery { favoriteLocalDataSource.removeFavorite(movieId) } coAnswers {
            println("Removed from data source")
        }
        // when
        favoriteRepository.removeFavorite(movieId)
        // then
        coVerify { favoriteLocalDataSource.removeFavorite(movieId) }
    }
}
