package com.nbs.moviedb

import android.app.Application
import timber.log.Timber

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
