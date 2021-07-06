package com.nbs.moviedb.presentation.home

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants.FROM_INDEX
import com.nbs.moviedb.domain.usecase.movie.Constants.HOME_ITEMS_SIZE
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
 * Created by aydbtiko on 7/6/2021.
 *
 */
@ExperimentalCoroutinesApi
class GetHomePopularMoviesImplTest {

    private lateinit var getHomePopularMovies: GetHomePopularMoviesImpl
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        responseBuilder = ResponseBuilder()
        getHomePopularMovies = GetHomePopularMoviesImpl(
            movieRepository = movieRepository
        )
    }

    @Test
    fun `get home popular movies contain expected values`() = runBlockingTest {
        // given
        val movies = responseBuilder.getPopularResponse().results.asDomainModels()
        every { movieRepository.getPopularMovie() } returns flowOf(movies)
        // when
        val result = getHomePopularMovies().first()
        println("$result")
        // then
        verify { movieRepository.getPopularMovie() }
        val expected = movies.subList(FROM_INDEX, HOME_ITEMS_SIZE)
        assertThat(result).containsExactlyElementsIn(expected)
    }

}