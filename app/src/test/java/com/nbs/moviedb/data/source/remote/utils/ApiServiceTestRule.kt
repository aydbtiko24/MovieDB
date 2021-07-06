package com.nbs.moviedb.data.source.remote.utils

import com.nbs.moviedb.data.source.remote.ApiService
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class ApiServiceTestRule(
    private val dispatcher: Dispatcher = SuccessDispatcher()
) : TestWatcher() {

    lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    val responseBuilder: ResponseBuilder?
        get() = (dispatcher as? SuccessDispatcher)?.responseBuilder

    override fun starting(description: Description?) {
        super.starting(description)
        apiService = buildTestApiService()
        mockWebServer = buildMockServer(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }
}
