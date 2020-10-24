package com.alex.comicdiscovery.feature.main.di

import com.alex.comicdiscovery.feature.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get()) }
}