package com.alex.comicdiscovery

import android.app.Application
import com.alex.features.Features
import com.alex.injector.Injector

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFeaturesModule()
        //setupTimber()
    }

    // ----------------------------------------------------------------------------

    private fun setupFeaturesModule() {
        Features.init(this)
        Injector.init(this)
    }

    /*
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
     */
}