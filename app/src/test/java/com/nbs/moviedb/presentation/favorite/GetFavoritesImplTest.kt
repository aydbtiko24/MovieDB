package com.nbs.moviedb.presentation.favorite

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.local.asDomainModels
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
class GetFavoritesImplTest {

    private lateinit var getFavorites: GetFavoritesImpl

    @MockK
    lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getFavorites = GetFavoritesImpl(
            favoriteRepository = favoriteRepository
        )
    }

    @Test
    fun `get favorites, search query empty`() = runBlockingTest {
        // given
        val favorites = favoriteEntities.asDomainModels()
        coEvery { favoriteRepository.getFavorites() } returns flowOf(favorites)
        // when
        val result = getFavorites("").first()
        // then
        coVerify { favoriteRepository.getFavorites() }
        assertThat(result).containsExactlyElementsIn(favorites)
    }

    @Test
    fun `get favorites, search query not empty`() = runBlockingTest {
        // given
        val favorites = favoriteEntities.asDomainModels()
        val favorite = favorites[1]
        coEvery { favoriteRepository.getFavorites() } returns flowOf(favorites)
        // when
        val result = getFavorites(favorite.title.lowercase()).first()
        // then
        coVerify { favoriteRepository.getFavorites() }
        assertThat(result).containsExactlyElementsIn(listOf(favorite))
    }
}
