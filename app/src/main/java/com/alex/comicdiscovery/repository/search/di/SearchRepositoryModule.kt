package com.alex.comicdiscovery.repository.search.di

import com.alex.comicdiscovery.repository.search.SearchRepository
import org.koin.dsl.module

val searchRepositoryModule = module {
    factory { SearchRepository(get()) }
}