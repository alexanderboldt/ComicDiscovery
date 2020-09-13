package com.example.comicdiscovery

import android.app.Application
import com.example.comicdiscovery.repository.api.ApiClient

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupRetrofit()
    }

    // ----------------------------------------------------------------------------

    private fun setupRetrofit() {
        ApiClient.init()
    }
}