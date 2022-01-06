package com.alex.features.feature.starlist.di

import com.alex.features.feature.starlist.StarlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val starlistModule = module {
    viewModel { StarlistViewModel(get()) }
}