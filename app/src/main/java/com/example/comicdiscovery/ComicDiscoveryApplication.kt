package com.example.comicdiscovery

import android.app.Application
import com.example.comicdiscovery.feature.base.di.resourceProviderModule
import com.example.comicdiscovery.feature.character.detail.di.characterDetailModule
import com.example.comicdiscovery.feature.character.overview.di.characterOverviewModule
import com.example.comicdiscovery.repository.api.ApiClient
import com.example.comicdiscovery.repository.character.di.characterRepositoryModule
import com.example.comicdiscovery.repository.search.di.searchRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupRetrofit()
        setupKoin()
    }

    // ----------------------------------------------------------------------------

    private fun setupRetrofit() {
        ApiClient.init()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@ComicDiscoveryApplication)
            modules(
                listOf(
                    characterOverviewModule,
                    characterDetailModule,
                    resourceProviderModule,
                    searchRepositoryModule,
                    characterRepositoryModule))
        }
    }
}