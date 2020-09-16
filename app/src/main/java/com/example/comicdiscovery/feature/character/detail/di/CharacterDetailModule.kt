package com.example.comicdiscovery.feature.character.detail.di

import com.example.comicdiscovery.feature.character.detail.CharacterDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterDetailModule = module {
    viewModel { CharacterDetailViewModel(get()) }
}