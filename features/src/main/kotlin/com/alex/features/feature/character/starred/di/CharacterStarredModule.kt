package com.alex.features.feature.character.starred.di

import com.alex.features.feature.character.starred.CharacterStarredViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterStarredModule = module {
    viewModel { CharacterStarredViewModel(get(), get()) }
}