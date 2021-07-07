package com.nbs.moviedb.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.MainCoroutineRule
import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.usecase.movie.Constants.FROM_INDEX
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_DISCOVER_SIZE
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_ITEMS_SIZE
import com.nbs.moviedb.domain.usecase.movie.GetHomeComingSoonMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomeDiscoverMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomePopularMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.ComingSoonMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.DiscoverMovies
import com.nbs.moviedb.presentation.home.HomeUiModel.Error
import com.nbs.moviedb.presentation.home.HomeUiModel.PopularMovies
import com.nbs.moviedb.presentation.utils.toYearQuery
import com.nbs.moviedb.presentation.utils.getOrWaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
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
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var getHomeDiscoverMovies: GetHomeDiscoverMovies

    @MockK
    lateinit var getHomePopularMovies: GetHomePopularMovies

    @MockK
    lateinit var getHomeComingSoonMovies: GetHomeComingSoonMovies

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var discoverMovies: List<Movie>
    private lateinit var popularMovies: List<Movie>
    private lateinit var comingSoonMovies: List<Movie>
    private val year = "2022"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        responseBuilder = ResponseBuilder()
        setUpDummyData()
    }

    @Test
    fun `get home ui models contains expected values`() = runBlockingTest {
        // given
        every { getHomeDiscoverMovies() } returns flowOf(discoverMovies)
        every { getHomePopularMovies() } returns flowOf(popularMovies)
        every { getHomeComingSoonMovies(year) } returns flowOf(comingSoonMovies)
        // when
        initViewModel()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        val expected = listOf(
            DiscoverMovies(discoverMovies),
            PopularMovies(items = popularMovies),
            ComingSoonMovies(items = comingSoonMovies)
        )
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(expected)
    }

    @Test
    fun `get home ui models error on discover`() = runBlockingTest {
        // given
        val errorMessage = "something when wrong"
        every { getHomeDiscoverMovies() } returns flow { throw Throwable(errorMessage) }
        every { getHomePopularMovies() } returns flowOf(popularMovies)
        every { getHomeComingSoonMovies(year) } returns flowOf(comingSoonMovies)
        // when
        initViewModel()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        val expected = listOf(Error(errorMessage))
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(expected)
    }

    @Test
    fun `get home ui models error on discover, retry get data`() = runBlockingTest {
        // given
        val errorMessage = "something when wrong"
        every { getHomeDiscoverMovies() } returns flow { throw Throwable(errorMessage) }
        every { getHomePopularMovies() } returns flowOf(popularMovies)
        every { getHomeComingSoonMovies(year) } returns flowOf(comingSoonMovies)
        initViewModel()
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        val errorExpected = listOf(Error(errorMessage))
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(errorExpected)
        // when
        every { getHomeDiscoverMovies() } returns flowOf(discoverMovies)
        viewModel.retryGetData()
        // then
        val expected = listOf(
            DiscoverMovies(discoverMovies),
            PopularMovies(items = popularMovies),
            ComingSoonMovies(items = comingSoonMovies)
        )
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(expected)
    }

    @Test
    fun `get home ui models, refresh data`() = runBlockingTest {
        // given
        every { getHomeDiscoverMovies() } returns flowOf(discoverMovies)
        every { getHomePopularMovies() } returns flowOf(popularMovies)
        every { getHomeComingSoonMovies(year) } returns flowOf(comingSoonMovies)
        initViewModel()
        // when
        viewModel.refreshData()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        val expected = listOf(
            DiscoverMovies(discoverMovies),
            PopularMovies(items = popularMovies),
            ComingSoonMovies(items = comingSoonMovies)
        )
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(expected)
    }

    @Test
    fun `get home ui models, refresh data, error on discover`() = runBlockingTest {
        // given
        every { getHomeDiscoverMovies() } returns flowOf(discoverMovies)
        every { getHomePopularMovies() } returns flowOf(popularMovies)
        every { getHomeComingSoonMovies(year) } returns flowOf(comingSoonMovies)
        initViewModel()
        // when
        val errorMessage = "something when wrong"
        every { getHomeDiscoverMovies() } returns flow {
            throw Throwable(errorMessage)
        }
        viewModel.refreshData()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        val expected = listOf(
            DiscoverMovies(discoverMovies),
            PopularMovies(items = popularMovies),
            ComingSoonMovies(items = comingSoonMovies)
        )
        assertThat(viewModel.homeUiModels.getOrWaitValue()).containsExactlyElementsIn(expected)
        assertThat(
            viewModel.loadingDataError.getOrWaitValue().getContentIfNotHandled()
        ).isEqualTo(errorMessage)
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            getHomeDiscoverMovies = getHomeDiscoverMovies,
            getHomePopularMovies = getHomePopularMovies,
            getHomeComingSoonMovies = getHomeComingSoonMovies
        )
    }

    private fun setUpDummyData() {
        mockkStatic("com.nbs.moviedb.presentation.utils.DateFormatterExtKt")
        every { any<Long>().toYearQuery() } returns year

        discoverMovies = responseBuilder.getDiscoverResponse().results.subList(
            FROM_INDEX,
            HOME_DISCOVER_SIZE
        ).map { it.asDomainModel() }

        popularMovies = responseBuilder.getPopularResponse().results.subList(
            FROM_INDEX,
            HOME_ITEMS_SIZE
        ).map { it.asDomainModel() }

        comingSoonMovies = responseBuilder.getComingSoonResponse().results.subList(
            FROM_INDEX,
            HOME_ITEMS_SIZE
        ).map { it.asDomainModel() }
    }
}
