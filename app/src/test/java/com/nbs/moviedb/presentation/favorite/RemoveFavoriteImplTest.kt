package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.data.source.local.favoriteEntities
import com.nbs.moviedb.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@ExperimentalCoroutinesApi
class RemoveFavoriteImplTest {

    private lateinit var removeFavorite: RemoveFavoriteImpl

    @MockK
    lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        removeFavorite = RemoveFavoriteImpl(
            favoriteRepository = favoriteRepository
        )
    }

    @Test
    fun `remove favorite invoked on repository`() = runBlockingTest {
        // given
        val movieId = favoriteEntities[1].movieId
        coEvery { favoriteRepository.removeFavorite(movieId) } coAnswers {
            println("invoked on repository")
        }
        // when
        removeFavorite(movieId)
        // then
        coVerify { favoriteRepository.removeFavorite(movieId) }
    }
}
