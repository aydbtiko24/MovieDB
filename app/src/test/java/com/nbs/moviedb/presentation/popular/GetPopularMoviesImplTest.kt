package com.nbs.moviedb.presentation.popular

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
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
class GetPopularMoviesImplTest {

    private lateinit var getPopularMovies: GetPopularMoviesImpl
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        responseBuilder = ResponseBuilder()
        getPopularMovies = GetPopularMoviesImpl(
            movieRepository = movieRepository
        )
    }

    @Test
    fun `get popular movies contain expected values`() = runBlockingTest {
        // given
        val movies = responseBuilder.getPopularResponse().results.asDomainModels()
        every { movieRepository.getPopularMovie() } returns flowOf(movies)
        // when
        val result = getPopularMovies().first()
        // then
        assertThat(result).containsExactlyElementsIn(movies)
    }
}
