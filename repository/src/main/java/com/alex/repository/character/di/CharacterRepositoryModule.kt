package com.alex.repository.character.di

import com.alex.repository.character.CharacterRepository
import org.koin.dsl.module

val characterRepositoryModule = module {
    factory { CharacterRepository(get(), get()) }
}