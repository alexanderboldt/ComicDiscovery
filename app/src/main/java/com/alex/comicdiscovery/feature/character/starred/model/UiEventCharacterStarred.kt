package com.alex.comicdiscovery.feature.character.starred.model

sealed class UiEventCharacterStarred {
    object StarlistSettingsScreen : UiEventCharacterStarred()
    data class DetailScreen(val id: Int) : UiEventCharacterStarred()
}