package com.nbs.moviedb.presentation.favorite

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.local.favoriteEntities
import com.nbs.moviedb.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class GetIsFavoriteImplTest {

    private lateinit var getIsFavorite: GetIsFavoriteImpl

    @MockK
    lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getIsFavorite = GetIsFavoriteImpl(
            favoriteRepository = favoriteRepository
        )
    }

    @Test
    fun `get is favorite invoked on repository`() = runBlockingTest {
        // given
        val movieId = favoriteEntities[1].movieId
        coEvery { favoriteRepository.isFavorite(movieId) } returns flowOf(true)
        // when
        val result = getIsFavorite(movieId).first()
        // then
        coVerify { favoriteRepository.isFavorite(movieId) }
        assertThat(result).isTrue()
    }
}
