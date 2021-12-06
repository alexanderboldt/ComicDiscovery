package com.alex.comicdiscovery.repository.profile.di

import com.alex.comicdiscovery.repository.profile.ProfileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val profileRepositoryModule = module {
    factory { ProfileRepository(androidContext(), get()) }
}