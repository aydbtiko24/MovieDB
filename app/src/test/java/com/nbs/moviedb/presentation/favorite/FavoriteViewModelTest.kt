package com.nbs.moviedb.presentation.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.MainCoroutineRule
import com.nbs.moviedb.data.source.local.asDomainModels
import com.nbs.moviedb.data.source.local.favoriteEntities
import com.nbs.moviedb.domain.usecase.favorite.GetFavorites
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite
import com.nbs.moviedb.presentation.utils.getOrWaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel
    private val favorites = favoriteEntities.asDomainModels()

    @MockK
    lateinit var getFavorites: GetFavorites

    @MockK
    lateinit var removeFavorite: RemoveFavorite

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get favorites contain expected values`() {
        // given
        val searchQuery = ""
        every { getFavorites(searchQuery) } returns flowOf(favorites)
        // when
        initViewModel()
        // then
        assertThat(
            viewModel.favorites.getOrWaitValue()
        ).containsExactlyElementsIn(favorites)
        verify { getFavorites(searchQuery) }
    }

    @Test
    fun `get empty favorites data is empty contain expected values`() {
        // given
        val searchQuery = ""
        every { getFavorites(searchQuery) } returns flowOf(emptyList())
        // when
        initViewModel()
        // then
        assertThat(
            viewModel.favorites.getOrWaitValue()
        ).isEmpty()
        assertThat(
            viewModel.dataIsEmpty.getOrWaitValue()
        ).isTrue()
    }

    @Test
    fun `get empty favorites, search query not empty, data is empty contain expected values`() {
        // given
        val searchQuery = favorites[0].title.lowercase()
        every { getFavorites("") } returns flowOf(favorites)
        initViewModel()
        // when
        every { getFavorites(searchQuery) } returns flowOf(emptyList())
        viewModel.searchFavorite(searchQuery)
        // then
        assertThat(
            viewModel.favorites.getOrWaitValue()
        ).isEmpty()
        assertThat(
            viewModel.dataIsEmpty.getOrWaitValue()
        ).isFalse()
        assertThat(
            viewModel.searchQuery.getOrWaitValue()
        ).isEqualTo(searchQuery)
        assertThat(
            viewModel.searchResultEnable.getOrWaitValue()
        ).isTrue()
    }

    @Test
    fun `remove favorite`() {
        // given
        val movieId = favorites[0].movieId
        val searchQuery = ""
        every { getFavorites(searchQuery) } returns flowOf(emptyList())
        initViewModel()
        // when
        coEvery { removeFavorite(movieId) } coAnswers {
            println("Removed to usecase")
        }
        viewModel.remove(movieId)
        // then
        coVerify { removeFavorite(movieId) }
    }

    private fun initViewModel() {
        viewModel = FavoriteViewModel(
            getFavorites = getFavorites,
            removeFavorite = removeFavorite
        )
    }
}
