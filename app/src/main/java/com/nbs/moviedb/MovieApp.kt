package com.nbs.moviedb

import android.app.Application
import com.nbs.moviedb.di.appModule
import com.nbs.moviedb.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(dataModule, appModule)
        }
        // logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
