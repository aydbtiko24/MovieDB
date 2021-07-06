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
class GetHomeComingSoonMoviesImplTest {

    private lateinit var getHomeComingSoonMovies: GetHomeComingSoonMoviesImpl
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        responseBuilder = ResponseBuilder()
        getHomeComingSoonMovies = GetHomeComingSoonMoviesImpl(
            movieRepository = movieRepository
        )
    }

    @Test
    fun `get home coming soon movies contain expected values`() = runBlockingTest {
        // given
        val year = "2022"
        val movies = responseBuilder.getComingSoonResponse().results.asDomainModels()
        every { movieRepository.getComingSoonMovie(year) } returns flowOf(movies)
        // when
        val result = getHomeComingSoonMovies(year).first()
        println("$result")
        // then
        verify { movieRepository.getComingSoonMovie(year) }
        val expected = movies.subList(FROM_INDEX, HOME_ITEMS_SIZE)
        assertThat(result).containsExactlyElementsIn(expected)
    }
}
