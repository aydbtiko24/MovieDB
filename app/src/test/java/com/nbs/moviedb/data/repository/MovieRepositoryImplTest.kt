package com.nbs.moviedb.data.repository

import com.nbs.moviedb.data.source.remote.MovieRemoteDataSource
import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.DETAIL_MOVIE_ID_PATH_VALUE
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private lateinit var movieRepository: MovieRepositoryImpl
    private lateinit var responseBuilder: ResponseBuilder

    @MockK
    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepository = MovieRepositoryImpl(
            movieRemoteDataSource = movieRemoteDataSource
        )
        responseBuilder = ResponseBuilder()
    }

    @Test
    fun `get discover invoked on remote data source`() = runBlockingTest {
        val movies = responseBuilder.getDiscoverResponse().results.asDomainModels()
        coEvery { movieRemoteDataSource.getDiscoverMovie() } returns movies
        // when
        movieRepository.getDiscoverMovie().first()
        // then
        coVerify { movieRemoteDataSource.getDiscoverMovie() }
    }

    @Test
    fun `get popular invoked on remote data source`() = runBlockingTest {
        val movies = responseBuilder.getPopularResponse().results.asDomainModels()
        coEvery { movieRemoteDataSource.getPopularMovie() } returns movies
        // when
        movieRepository.getPopularMovie().first()
        // then
        coVerify { movieRemoteDataSource.getPopularMovie() }
    }

    @Test
    fun `get coming soon invoked on remote data source`() = runBlockingTest {
        val year = "2022"
        val movies = responseBuilder.getPopularResponse().results.asDomainModels()
        coEvery { movieRemoteDataSource.getComingSoonMovie(year) } returns movies
        // when
        movieRepository.getComingSoonMovie(year).first()
        // then
        coVerify { movieRemoteDataSource.getComingSoonMovie(year) }
    }

    @Test
    fun `get detail movie invoked on remote data source`() = runBlockingTest {
        val movieId = DETAIL_MOVIE_ID_PATH_VALUE
        val detailMovie = responseBuilder.getDetailResponse().asDomainModel()
        coEvery { movieRemoteDataSource.getDetailMovie(movieId) } returns detailMovie
        // when
        movieRepository.getDetailMovie(movieId).first()
        // then
        coVerify { movieRemoteDataSource.getDetailMovie(movieId) }
    }

    @Test
    fun `get movie cast invoked on remote data source`() = runBlockingTest {
        val movieId = DETAIL_MOVIE_ID_PATH_VALUE
        val movieCasts = responseBuilder.getCastResponse().casts.asDomainModels()
        coEvery { movieRemoteDataSource.getMovieCast(movieId) } returns movieCasts
        // when
        movieRepository.getMovieCast(movieId).first()
        // then
        coVerify { movieRemoteDataSource.getMovieCast(movieId) }
    }
}
