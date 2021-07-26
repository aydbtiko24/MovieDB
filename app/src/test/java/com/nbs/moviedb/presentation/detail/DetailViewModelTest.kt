package com.nbs.moviedb.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.MainCoroutineRule
import com.nbs.moviedb.data.source.local.asFavorite
import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.DETAIL_MOVIE_ID_PATH_VALUE
import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.usecase.favorite.AddFavorite
import com.nbs.moviedb.domain.usecase.favorite.GetIsFavorite
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite
import com.nbs.moviedb.domain.usecase.movie.GetDetailMovie
import com.nbs.moviedb.domain.usecase.movie.GetMovieCast
import com.nbs.moviedb.presentation.utils.ErrorState
import com.nbs.moviedb.presentation.utils.getOrWaitValue
import com.nbs.moviedb.presentation.utils.observeForTesting
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @MockK
    lateinit var getDetailMovie: GetDetailMovie

    @MockK
    lateinit var getMovieCast: GetMovieCast

    @MockK
    lateinit var getIsFavorite: GetIsFavorite

    @MockK
    lateinit var addFavorite: AddFavorite

    @MockK
    lateinit var removeFavorite: RemoveFavorite
    private val movieId = DETAIL_MOVIE_ID_PATH_VALUE

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailMovie: DetailMovie
    private lateinit var movieCast:List<Cast>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val responseBuilder = ResponseBuilder()
        detailMovie = responseBuilder.getDetailResponse().asDomainModel()
        movieCast = responseBuilder.getCastResponse().casts.asDomainModels()
    }

    @Test
    fun `get detail movie contains expected values`() {
        // given
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(false)
        // when
        initViewModel()
        // then
        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        assertThat(viewModel.detailMovie.getOrWaitValue()).isEqualTo(detailMovie)
        assertThat(
            viewModel.movieCasts.getOrWaitValue()
        ).containsExactlyElementsIn(movieCast)
    }

    @Test
    fun `get favorite movie contains expected values`() {
        // given
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(true)
        // when
        initViewModel()
        // then
        assertThat(viewModel.favoriteUiState.getOrWaitValue().favorite).isTrue()
    }

    @Test
    fun `get un-favorite movie contains expected values`() {
        // given
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(false)
        // when
        initViewModel()
        // then
        assertThat(viewModel.favoriteUiState.getOrWaitValue().favorite).isFalse()
    }

    @Test
    fun `edit favorite movie contains expected values`() = runBlockingTest {
        // given
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(true)
        initViewModel()
        viewModel.favoriteUiState.observeForTesting {
            // when
            coEvery { removeFavorite(movieId) } coAnswers {
                println("Removed from source")
            }
            viewModel.editFavorite()
            // then
            coVerify { removeFavorite(movieId) }
        }
    }

    @Test
    fun `edit un favorite movie contains expected values`() = runBlockingTest {
        // given
        val favorite = detailMovie.asFavorite()
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(false)
        initViewModel()
        viewModel.favoriteUiState.observeForTesting {
            // when
            coEvery { addFavorite(favorite) } coAnswers {
                println("Added to source")
            }
            viewModel.editFavorite()
            // then
            coVerify { addFavorite(favorite) }
        }
    }

    @Test
    fun `get detail movie, error on server, retry get data`() {
        // given
        val errorMessage = "something when wrong"
        every { getDetailMovie(movieId) } returns flow { throw Throwable(errorMessage) }
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(false)

        initViewModel()

        assertThat(viewModel.loadingData.getOrWaitValue()).isFalse()
        assertThat(
            viewModel.errorState.getOrWaitValue()
        ).isEqualTo(ErrorState(message = errorMessage, visible = true))
        // when
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        viewModel.retryGetData()
        // then
        assertThat(viewModel.detailMovie.getOrWaitValue()).isEqualTo(detailMovie)
        assertThat(
            viewModel.errorState.getOrWaitValue()
        ).isEqualTo(ErrorState.Initial)
        assertThat(
            viewModel.movieCasts.getOrWaitValue()
        ).containsExactlyElementsIn(movieCast)
    }

    @Test
    fun `get detail movie, error on refresh data`() {
        // given
        every { getDetailMovie(movieId) } returns flowOf(detailMovie)
        every { getMovieCast(movieId) } returns flowOf(movieCast)
        every { getIsFavorite(movieId) } returns flowOf(false)
        initViewModel()
        // when
        val errorMessage = "something when wrong"
        every { getDetailMovie(movieId) } returns flow { throw Throwable(errorMessage) }
        viewModel.getDetailMovieData()
        // then
        assertThat(viewModel.detailMovie.getOrWaitValue()).isEqualTo(detailMovie)
        assertThat(
            viewModel.movieCasts.getOrWaitValue()
        ).containsExactlyElementsIn(movieCast)
        assertThat(
            viewModel.errorState.getOrWaitValue()
        ).isEqualTo(ErrorState.Initial)
        assertThat(
            viewModel.loadingDataError.getOrWaitValue().getContentIfNotHandled()
        ).isEqualTo(errorMessage)
    }

    private fun initViewModel() {
        viewModel = DetailViewModel(
            movieId = movieId,
            getDetailMovie = getDetailMovie,
            getMovieCast = getMovieCast,
            getIsFavorite = getIsFavorite,
            addFavorite = addFavorite,
            removeFavorite = removeFavorite
        )
    }
}
