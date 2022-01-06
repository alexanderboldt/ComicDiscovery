package com.alex.comicdiscovery

import android.app.Application
import com.alex.features.FeaturesModule

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFeaturesModule()
        //setupTimber()
    }

    // ----------------------------------------------------------------------------

    private fun setupFeaturesModule() {
        FeaturesModule.init(this)
    }

    /*
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
     */
}