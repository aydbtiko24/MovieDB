package com.nbs.moviedb.presentation.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.MainCoroutineRule
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.presentation.utils.getOrWaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@ExperimentalCoroutinesApi
class PopularViewModelTest {

    private lateinit var viewModel: PopularViewModel
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var getPopularMovies: GetPopularMoviesImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var popularMovies: List<Movie>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        responseBuilder = ResponseBuilder()
        popularMovies = responseBuilder.getPopularResponse().results.asDomainModels()
    }

    @Test
    fun `get popular movies contain expected values`() = runBlockingTest {
        // given
        every { getPopularMovies() } returns flowOf(popularMovies)
        // when
        initViewModel()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(popularMovies)
    }

    @Test
    fun `get popular movies error on response`() = runBlockingTest {
        // given
        val errorMessage = "something when wrong"
        every { getPopularMovies() } returns flow { throw Throwable(errorMessage) }
        // when
        initViewModel()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        assertThat(
            viewModel.loadingDataError.getOrWaitValue().getContentIfNotHandled()
        ).isEqualTo(errorMessage)
    }

    @Test
    fun `get popular movies error on response, retry get data`() = runBlockingTest {
        // given
        val errorMessage = "something when wrong"
        every { getPopularMovies() } returns flow { throw Throwable(errorMessage) }
        initViewModel()
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        assertThat(
            viewModel.loadingDataError.getOrWaitValue().getContentIfNotHandled()
        ).isEqualTo(errorMessage)
        // when
        every { getPopularMovies() } returns flowOf(popularMovies)
        viewModel.getPopularData()
        // then

        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(popularMovies)
    }

    @Test
    fun `get popular movies, search popular movie`() = runBlockingTest {
        // given
        val movie = popularMovies[3]
        every { getPopularMovies() } returns flowOf(popularMovies)
        initViewModel()
        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(popularMovies)
        // when
        viewModel.searchMovie(movie.title)
        advanceUntilIdle()
        // then
        assertThat(viewModel.searchQuery.getOrWaitValue()).isEqualTo(movie.title)
        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(
            listOf(movie)
        )
        assertThat(viewModel.searchResultEnable.getOrWaitValue()).isTrue()
    }

    @Test
    fun `get popular movies, search popular movie, clear search`() = runBlockingTest {
        // given
        val movie = popularMovies[3]
        every { getPopularMovies() } returns flowOf(popularMovies)
        initViewModel()
        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(popularMovies)

        viewModel.searchMovie(movie.title)
        advanceUntilIdle()

        assertThat(viewModel.searchQuery.getOrWaitValue()).isEqualTo(movie.title)
        assertThat(
            viewModel.popularMovies.getOrWaitValue()
        ).containsExactlyElementsIn(
            listOf(movie)
        )
        assertThat(viewModel.searchResultEnable.getOrWaitValue()).isTrue()

        // when
        viewModel.clearSearchQuery()

        // then
        assertThat(viewModel.searchQuery.getOrWaitValue()).isEmpty()
        assertThat(viewModel.searchResultEnable.getOrWaitValue()).isFalse()
    }

    private fun initViewModel() {
        viewModel = PopularViewModel(
            getPopularMovies = getPopularMovies
        )
    }

}