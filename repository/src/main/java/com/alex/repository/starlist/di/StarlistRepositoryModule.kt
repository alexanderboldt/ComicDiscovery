package com.alex.repository.starlist.di

import com.alex.repository.starlist.StarlistRepository
import org.koin.dsl.module

val starlistRepositoryModule = module {
    factory { StarlistRepository(get(), get()) }
}