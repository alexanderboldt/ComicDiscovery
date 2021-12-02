package com.alex.comicdiscovery.repository.file.di

import com.alex.comicdiscovery.repository.file.FileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val fileRepositoryModule = module {
    factory { FileRepository(androidContext()) }
}