package com.alex.features.feature.character.starred.model

sealed interface SideEffect {
    object StarlistSettingsScreen : SideEffect
    data class CharacterDetailScreen(val id: Int) : SideEffect
}