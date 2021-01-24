package com.alex.comicdiscovery.repository.session.di

import com.alex.comicdiscovery.repository.session.SessionRepository
import org.koin.dsl.module

val sessionRepositoryModule = module {
    factory { SessionRepository(get(), get()) }
}