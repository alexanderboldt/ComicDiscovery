package com.alex.comicdiscovery.repository.starlist.di

import com.alex.comicdiscovery.repository.starlist.StarlistRepository
import org.koin.dsl.module

val starlistRepositoryModule = module {
    factory { StarlistRepository(get(), get()) }
}