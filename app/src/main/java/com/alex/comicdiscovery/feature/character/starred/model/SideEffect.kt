package com.alex.comicdiscovery.feature.character.starred.model

sealed class SideEffect {
    object StarlistSettingsScreen : SideEffect()
    data class CharacterDetailScreen(val id: Int) : SideEffect()
}