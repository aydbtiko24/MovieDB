package com.nbs.moviedb.data.source.remote.utils

import com.nbs.moviedb.BuildConfig.API_KEY
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.COMING_SOON_RESPONSE_NAME
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.DISCOVER_RESPONSE_NAME
import com.nbs.moviedb.data.source.remote.utils.ResponseBuilder.Companion.POPULAR_RESPONSE_NAME
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class SuccessDispatcher : Dispatcher() {

    val responseBuilder = ResponseBuilder()

    // mock response
    private val mockResponse = MockResponse().setResponseCode(200)
    private val errorMockResponse = MockResponse().setResponseCode(400)

    // api path
    private val comingSoonPath = "/discover/movie?api_key=$API_KEY&language=en-US&sort_by" +
            "=popularity.desc&include_adult=false&include_video=false&page=1&year=2022"
    private val discoverPath = "/discover/movie?api_key=$API_KEY&language=en-US&sort_by" +
            "=popularity.desc&include_adult=false&include_video=false&page=1"
    private val popularPath = "/discover/movie?api_key=$API_KEY&language=en-US&sort_by" +
            "=popularity.desc&include_adult=false&include_video=false&page=2"

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            comingSoonPath -> mockResponse.setBody(
                responseBuilder.get(COMING_SOON_RESPONSE_NAME)
            )
            discoverPath -> mockResponse.setBody(
                responseBuilder.get(DISCOVER_RESPONSE_NAME)
            )
            popularPath -> mockResponse.setBody(
                responseBuilder.get(POPULAR_RESPONSE_NAME)
            )
            else -> errorMockResponse
        }
    }
}
