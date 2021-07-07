package com.nbs.moviedb.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.data.source.remote.models.asDomainModel
import com.nbs.moviedb.data.source.remote.models.asDomainModels
import com.nbs.moviedb.data.source.remote.utils.ApiServiceTestRule
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.DETAIL_MOVIE_ID_PATH_VALUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
@ExperimentalCoroutinesApi
class MovieRemoteDataSourceImplTest {

    private lateinit var movieRemoteDataSource: MovieRemoteDataSourceImpl

    @get:Rule
    val apiServiceTestRule = ApiServiceTestRule()

    @Before
    fun setUp() {
        movieRemoteDataSource = MovieRemoteDataSourceImpl(
            apiService = apiServiceTestRule.apiService
        )
    }

    @Test
    fun `get discover movie, contain expected values`(): Unit = runBlocking {
        val movies =
            apiServiceTestRule.responseBuilder!!.getDiscoverResponse().results.asDomainModels()
        // when
        val result = movieRemoteDataSource.getDiscoverMovie()
        // then
        assertThat(result).containsExactlyElementsIn(movies)
    }

    @Test
    fun `get popular movie, contain expected values`(): Unit = runBlocking {
        val movies =
            apiServiceTestRule.responseBuilder!!.getPopularResponse().results.asDomainModels()
        // when
        val result = movieRemoteDataSource.getPopularMovie()
        // then
        assertThat(result).containsExactlyElementsIn(movies)
    }

    @Test
    fun `get coming soon movie, contain expected values`(): Unit = runBlocking {
        val year = "2022"
        val movies =
            apiServiceTestRule.responseBuilder!!.getComingSoonResponse().results.asDomainModels()
        // when
        val result = movieRemoteDataSource.getComingSoonMovie(year)
        // then
        assertThat(result).containsExactlyElementsIn(movies)
    }

    @Test
    fun `get detail movie, contain expected values`(): Unit = runBlocking {
        val movieId = DETAIL_MOVIE_ID_PATH_VALUE
        val detailMovie = apiServiceTestRule.responseBuilder!!.getDetailResponse().asDomainModel()
        // when
        val result = movieRemoteDataSource.getDetailMovie(movieId)
        // then
        assertThat(result).isEqualTo(detailMovie)
    }
}
