package com.alex.features

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.alex.features.feature.base.ResourceProvider
import com.alex.features.feature.base.WidgetManager
import com.alex.features.feature.character.detail.CharacterDetailViewModel
import com.alex.features.feature.character.overview.CharacterOverviewViewModel
import com.alex.features.feature.character.starred.CharacterStarredViewModel
import com.alex.features.feature.home.HomeViewModel
import com.alex.features.feature.main.MainViewModel
import com.alex.features.feature.profile.ProfileViewModel
import com.alex.features.feature.settings.SettingsViewModel
import com.alex.features.feature.starlist.StarlistViewModel
import com.alex.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
object FeaturesModule {

    fun init() {
        RepositoryModule.init()

        loadKoinModules(
            module {
                // resource classes
                factory { androidContext().resources }
                factoryOf(::ResourceProvider)
                factoryOf(::WidgetManager)

                // feature ViewModels
                viewModelOf(::MainViewModel)
                viewModelOf(::HomeViewModel)
                viewModelOf(::StarlistViewModel)
                viewModelOf(::CharacterOverviewViewModel)
                viewModelOf(::CharacterStarredViewModel)
                viewModelOf(::CharacterDetailViewModel)
                viewModelOf(::SettingsViewModel)
                viewModelOf(::ProfileViewModel)
            }
        )
    }
}