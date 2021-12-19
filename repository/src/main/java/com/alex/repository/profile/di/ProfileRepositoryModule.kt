package com.alex.repository.profile.di

import com.alex.repository.profile.ProfileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val profileRepositoryModule = module {
    factory { ProfileRepository(androidContext(), get()) }
}