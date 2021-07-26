package com.nbs.moviedb.data.source.remote.utils

import com.nbs.moviedb.data.source.remote.models.CastDto
import com.nbs.moviedb.data.source.remote.models.CastResponse
import com.nbs.moviedb.data.source.remote.models.DetailMovieDto
import com.nbs.moviedb.data.source.remote.models.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStreamReader

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class ResponseBuilder {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun get(fileName: String): String {
        return InputStreamReader(javaClass.classLoader!!.getResourceAsStream(fileName)).use {
            it.readText()
        }
    }

    private val invalidResponseException = Exception("can't read response")

    fun getComingSoonResponse(): MovieResponse {
        val response = get(COMING_SOON_RESPONSE_NAME)
        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
        return jsonAdapter.fromJson(response) ?: throw invalidResponseException
    }

    fun getDiscoverResponse(): MovieResponse {
        val response = get(DISCOVER_RESPONSE_NAME)
        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
        return jsonAdapter.fromJson(response) ?: throw invalidResponseException
    }

    fun getPopularResponse(): MovieResponse {
        val response = get(POPULAR_RESPONSE_NAME)
        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
        return jsonAdapter.fromJson(response) ?: throw invalidResponseException
    }

    fun getDetailResponse(): DetailMovieDto {
        val response = get(DETAIL_RESPONSE_NAME)
        val jsonAdapter = moshi.adapter(DetailMovieDto::class.java)
        return jsonAdapter.fromJson(response) ?: throw invalidResponseException
    }

    fun getCastResponse(): CastResponse {
        val response = get(CAST_RESPONSE_NAME)
        val jsonAdapter = moshi.adapter(CastResponse::class.java)
        return jsonAdapter.fromJson(response) ?: throw invalidResponseException
    }

    companion object {

        const val COMING_SOON_RESPONSE_NAME = "coming_soon_response.json"
        const val DISCOVER_RESPONSE_NAME = "discover_response.json"
        const val POPULAR_RESPONSE_NAME = "popular_response.json"
        const val DETAIL_RESPONSE_NAME = "detail_response.json"
        const val CAST_RESPONSE_NAME = "cast_response.json"

        // path value
        const val DETAIL_MOVIE_ID_PATH_VALUE: Long = 520763
    }
}
