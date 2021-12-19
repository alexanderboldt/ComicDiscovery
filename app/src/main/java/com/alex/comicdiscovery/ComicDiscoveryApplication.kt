package com.alex.comicdiscovery

import android.app.Application
import com.alex.comicdiscovery.feature.base.di.resourceProviderModule
import com.alex.comicdiscovery.feature.character.detail.di.characterDetailModule
import com.alex.comicdiscovery.feature.character.overview.di.characterOverviewModule
import com.alex.comicdiscovery.feature.character.starred.di.characterStarredModule
import com.alex.comicdiscovery.feature.home.di.homeModule
import com.alex.comicdiscovery.feature.main.di.mainModule
import com.alex.comicdiscovery.feature.profile.di.profileModule
import com.alex.comicdiscovery.feature.settings.di.settingsModule
import com.alex.comicdiscovery.feature.starlist.di.starlistModule
import com.alex.repository.character.di.characterRepositoryModule
import com.alex.repository.datasource.api.di.apiRoutesModule
import com.alex.repository.datasource.database.ComicDiscoveryDatabase
import com.alex.repository.datasource.database.di.comicDiscoveryDatabaseModule
import com.alex.repository.datasource.datastore.profile.di.profileDataStoreModule
import com.alex.repository.datasource.datastore.settings.di.settingsDataStoreModule
import com.alex.repository.profile.di.profileRepositoryModule
import com.alex.repository.search.di.searchRepositoryModule
import com.alex.repository.settings.di.settingsRepositoryModule
import com.alex.repository.starlist.di.starlistRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ComicDiscoveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDatabase()
        setupKoin()
        setupTimber()
    }

    // ----------------------------------------------------------------------------

    private fun setupDatabase() {
        ComicDiscoveryDatabase.init(this)
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@ComicDiscoveryApplication)
            modules(listOf(
                // features
                mainModule,
                homeModule,
                starlistModule,
                characterOverviewModule,
                characterStarredModule,
                characterDetailModule,
                settingsModule,
                profileModule,
                // resource
                resourceProviderModule,
                // repository
                searchRepositoryModule,
                starlistRepositoryModule,
                characterRepositoryModule,
                settingsRepositoryModule,
                profileRepositoryModule,
                profileDataStoreModule,
                settingsDataStoreModule,
                apiRoutesModule,
                comicDiscoveryDatabaseModule))
        }
    }

    private fun setupTimber() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}