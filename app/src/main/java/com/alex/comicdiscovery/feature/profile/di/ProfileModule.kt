package com.alex.comicdiscovery.feature.profile.di

import com.alex.comicdiscovery.feature.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel(get()) }
}