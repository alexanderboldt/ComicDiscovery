package com.alex.features

import android.content.Context
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Features {

    fun init(context: Context) {
        Repository.init(context)

        loadKoinModules(
            module {
                // features
                viewModel { MainViewModel(get()) }
                viewModel { HomeViewModel() }
                viewModel { StarlistViewModel(get()) }
                viewModel { CharacterOverviewViewModel(get(), get()) }
                viewModel { CharacterStarredViewModel(get(), get()) }
                viewModel { (name: Int, userComesFromStarredScreen: Boolean) ->
                    CharacterDetailViewModel(name, userComesFromStarredScreen, get(), get(), get(), get())
                }
                viewModel { SettingsViewModel(get()) }
                viewModel { ProfileViewModel(get()) }
                // resource
                single { ResourceProvider(androidContext().resources) }
                single { WidgetManager(androidContext()) }
            }
        )
    }
}