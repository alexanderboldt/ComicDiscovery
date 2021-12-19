package com.alex.repository.search.di

import com.alex.repository.search.SearchRepository
import org.koin.dsl.module

val searchRepositoryModule = module {
    factory { SearchRepository(get()) }
}