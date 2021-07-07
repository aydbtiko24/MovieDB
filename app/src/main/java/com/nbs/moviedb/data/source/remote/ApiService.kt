package com.nbs.moviedb.data.source.remote

import com.nbs.moviedb.BuildConfig.API_KEY
import com.nbs.moviedb.data.source.remote.models.DetailMovieDto
import com.nbs.moviedb.data.source.remote.models.MovieResponse
import com.nbs.moviedb.domain.repository.ApiConstants.BASE_URL
import com.nbs.moviedb.domain.repository.ApiConstants.detailPath
import com.nbs.moviedb.domain.repository.ApiConstants.discoverPath
import com.nbs.moviedb.domain.repository.ApiConstants.includeAdultQuery
import com.nbs.moviedb.domain.repository.ApiConstants.includeAdultQueryValue
import com.nbs.moviedb.domain.repository.ApiConstants.includeVideoQuery
import com.nbs.moviedb.domain.repository.ApiConstants.includeVideoQueryValue
import com.nbs.moviedb.domain.repository.ApiConstants.keyQuery
import com.nbs.moviedb.domain.repository.ApiConstants.languageQuery
import com.nbs.moviedb.domain.repository.ApiConstants.languageQueryValue
import com.nbs.moviedb.domain.repository.ApiConstants.movieIdPath
import com.nbs.moviedb.domain.repository.ApiConstants.pageQuery
import com.nbs.moviedb.domain.repository.ApiConstants.pageQueryPopularValue
import com.nbs.moviedb.domain.repository.ApiConstants.pageQueryValue
import com.nbs.moviedb.domain.repository.ApiConstants.sortByQuery
import com.nbs.moviedb.domain.repository.ApiConstants.sortByQueryValue
import com.nbs.moviedb.domain.repository.ApiConstants.yearQuery
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber

interface ApiService {

    @GET(discoverPath)
    suspend fun getDiscoverMovies(
        @Query(keyQuery) apiKey: String = API_KEY,
        @Query(languageQuery) language: String = languageQueryValue,
        @Query(sortByQuery) sortBy: String = sortByQueryValue,
        @Query(includeAdultQuery) includeAdult: Boolean = includeAdultQueryValue,
        @Query(includeVideoQuery) includeVideo: Boolean = includeVideoQueryValue,
        @Query(pageQuery) page: Int = pageQueryValue
    ): MovieResponse

    @GET(discoverPath)
    suspend fun getPopularMovies(
        @Query(keyQuery) apiKey: String = API_KEY,
        @Query(languageQuery) language: String = languageQueryValue,
        @Query(sortByQuery) sortBy: String = sortByQueryValue,
        @Query(includeAdultQuery) includeAdult: Boolean = includeAdultQueryValue,
        @Query(includeVideoQuery) includeVideo: Boolean = includeVideoQueryValue,
        @Query(pageQuery) page: Int = pageQueryPopularValue
    ): MovieResponse

    @GET(discoverPath)
    suspend fun getComingSoonMovies(
        @Query(keyQuery) apiKey: String = API_KEY,
        @Query(languageQuery) language: String = languageQueryValue,
        @Query(sortByQuery) sortBy: String = sortByQueryValue,
        @Query(includeAdultQuery) includeAdult: Boolean = includeAdultQueryValue,
        @Query(includeVideoQuery) includeVideo: Boolean = includeVideoQueryValue,
        @Query(pageQuery) page: Int = pageQueryValue,
        @Query(yearQuery) year: String
    ): MovieResponse

    @GET(detailPath)
    suspend fun getDetailMovie(
        @Path(movieIdPath) movieId: Long,
        @Query(keyQuery) apiKey: String = API_KEY,
        @Query(languageQuery) language: String = languageQueryValue,
    ): DetailMovieDto

    companion object {

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor {
                Timber.tag("OkHttp").d(it)
            }.apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiService::class.java)
        }
    }
}
