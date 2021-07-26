package com.nbs.moviedb.presentation.detail

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.movie.Constants
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
class GetMovieCastImplTest {

    private lateinit var getMovieCast: GetMovieCastImpl

    @MockK
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieCast = GetMovieCastImpl(
            movieRepository = movieRepository
        )
    }

    @Test
    fun `get movie cast invoked on repository`() = runBlockingTest {
        // given
        val movieId = ResponseBuilder.DETAIL_MOVIE_ID_PATH_VALUE
        val movieCasts = ResponseBuilder().getCastResponse().casts.filter {
            it.knownDepartment == Constants.ACTING_DEPARTMENT
        }.asDomainModels()
        every { movieRepository.getMovieCast(movieId) } returns flowOf(movieCasts)
        // when
        val result = getMovieCast(movieId).first()
        // then
        assertThat(result).containsExactlyElementsIn(movieCasts)
        verify { movieRepository.getMovieCast(movieId) }
    }
}
