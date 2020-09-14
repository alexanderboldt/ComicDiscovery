package com.example.comicdiscovery.repository.search.di

import com.example.comicdiscovery.repository.search.SearchRepository
import org.koin.dsl.module

val searchRepositoryModule = module {
    factory { SearchRepository() }
}