package com.alex.comicdiscovery

import android.app.Application
import com.alex.features.FeaturesModule
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        setupFeaturesModule()
        setupLogcat()
    }

    // ----------------------------------------------------------------------------

    private fun setupKoin() {
        startKoin {
            androidContext(this@ComicDiscoveryApplication)
        }
    }

    private fun setupFeaturesModule() {
        FeaturesModule.init()
    }

    private fun setupLogcat() {
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}