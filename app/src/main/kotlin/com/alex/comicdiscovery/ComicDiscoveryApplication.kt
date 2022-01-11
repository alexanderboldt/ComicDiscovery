package com.alex.comicdiscovery

import android.app.Application
import com.alex.features.Features
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        setupFeatures()
        //setupTimber()
    }

    // ----------------------------------------------------------------------------

    private fun setupKoin() {
        startKoin {
            androidContext(this@ComicDiscoveryApplication)
        }
    }

    private fun setupFeatures() {
        Features.init(this)
    }

    /*
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
     */
}