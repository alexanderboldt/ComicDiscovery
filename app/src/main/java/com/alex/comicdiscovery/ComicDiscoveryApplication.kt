package com.alex.comicdiscovery

import android.app.Application
import com.alex.comicdiscovery.feature.base.di.resourceProviderModule
import com.alex.comicdiscovery.feature.character.detail.di.characterDetailModule
import com.alex.comicdiscovery.feature.character.overview.di.characterOverviewModule
import com.alex.comicdiscovery.feature.character.starred.di.characterStarredModule
import com.alex.comicdiscovery.feature.settings.di.settingsModule
import com.alex.comicdiscovery.repository.character.di.characterRepositoryModule
import com.alex.comicdiscovery.repository.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.search.di.searchRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDatabase()
        setupKoin()
    }

    // ----------------------------------------------------------------------------

    private fun setupDatabase() {
        ComicDiscoveryDatabase.init(this)
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@ComicDiscoveryApplication)
            modules(
                listOf(
                    // features
                    characterOverviewModule,
                    characterStarredModule,
                    characterDetailModule,
                    settingsModule,
                    // resource
                    resourceProviderModule,
                    // repository
                    searchRepositoryModule,
                    characterRepositoryModule))
        }
    }
}