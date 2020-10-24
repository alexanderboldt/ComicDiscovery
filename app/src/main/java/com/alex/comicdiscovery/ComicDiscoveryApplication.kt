package com.alex.comicdiscovery

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.alex.comicdiscovery.feature.base.di.resourceProviderModule
import com.alex.comicdiscovery.feature.character.detail.di.characterDetailModule
import com.alex.comicdiscovery.feature.character.overview.di.characterOverviewModule
import com.alex.comicdiscovery.feature.character.starred.di.characterStarredModule
import com.alex.comicdiscovery.feature.main.di.mainModule
import com.alex.comicdiscovery.feature.settings.di.settingsModule
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes
import com.alex.comicdiscovery.repository.character.di.characterRepositoryModule
import com.alex.comicdiscovery.repository.database.ComicDiscoveryDatabase
import com.alex.comicdiscovery.repository.models.RpModelTheme
import com.alex.comicdiscovery.repository.search.di.searchRepositoryModule
import com.alex.comicdiscovery.repository.settings.SettingsRepository
import com.alex.comicdiscovery.repository.settings.di.settingsRepositoryModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
                    mainModule,
                    characterOverviewModule,
                    characterStarredModule,
                    characterDetailModule,
                    settingsModule,
                    // resource
                    resourceProviderModule,
                    // repository
                    searchRepositoryModule,
                    characterRepositoryModule,
                    settingsRepositoryModule))
        }
    }
}