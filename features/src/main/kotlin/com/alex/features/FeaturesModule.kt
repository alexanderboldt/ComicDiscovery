package com.alex.features

import android.content.Context
import com.alex.features.feature.base.di.resourceProviderModule
import com.alex.features.feature.character.detail.di.characterDetailModule
import com.alex.features.feature.character.overview.di.characterOverviewModule
import com.alex.features.feature.character.starred.di.characterStarredModule
import com.alex.features.feature.home.di.homeModule
import com.alex.features.feature.main.di.mainModule
import com.alex.features.feature.profile.di.profileModule
import com.alex.features.feature.settings.di.settingsModule
import com.alex.features.feature.starlist.di.starlistModule
import com.alex.repository.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object FeaturesModule {

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(
                listOf(
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
            )
        }
    }
}