package com.alex.injector

import android.content.Context
import com.alex.api.ApiClient
import com.alex.database.ComicDiscoveryDatabase
import com.alex.datastore.profile.ProfileDataStore
import com.alex.datastore.settings.SettingsDataStore
import com.alex.features.feature.base.di.resourceProviderModule
import com.alex.features.feature.character.detail.di.characterDetailModule
import com.alex.features.feature.character.overview.di.characterOverviewModule
import com.alex.features.feature.character.starred.di.characterStarredModule
import com.alex.features.feature.home.di.homeModule
import com.alex.features.feature.main.di.mainModule
import com.alex.features.feature.profile.di.profileModule
import com.alex.features.feature.settings.di.settingsModule
import com.alex.features.feature.starlist.di.starlistModule
import com.alex.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val repositoryModule = module {
    factory { ApiClient.routes }
    factory { ComicDiscoveryDatabase }
    factory { ProfileDataStore(androidContext()) }
    factory { SettingsDataStore(androidContext()) }

    factory { CharacterRepository(get(), get()) }
    factory { ProfileRepository(androidContext(), get()) }
    factory { SearchRepository(get()) }
    factory { SettingsRepository(get()) }
    factory { StarlistRepository(get(), get()) }
}

object Injector {

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(
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
                repositoryModule
            )
        }
    }
}