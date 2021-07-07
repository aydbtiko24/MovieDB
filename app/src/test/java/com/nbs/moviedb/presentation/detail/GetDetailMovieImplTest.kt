package com.nbs.moviedb.presentation.detail

import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.DETAIL_MOVIE_ID_PATH_VALUE
import com.nbs.moviedb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
class GetDetailMovieImplTest {

    private lateinit var getDetailMovie: GetDetailMovieImpl

    @MockK
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getDetailMovie = GetDetailMovieImpl(
            movieRepository = movieRepository
        )
    }

    @Test
    fun `get detail movie invoked on repository`() = runBlockingTest {
        // given
        val movieId = DETAIL_MOVIE_ID_PATH_VALUE
        val detailMovie = ResponseBuilder().getDetailResponse().asDomainModel()
        every { movieRepository.getDetailMovie(movieId) } returns flowOf(detailMovie)
        // when
        getDetailMovie(movieId).first()
        // then
        verify { movieRepository.getDetailMovie(movieId) }
    }
}