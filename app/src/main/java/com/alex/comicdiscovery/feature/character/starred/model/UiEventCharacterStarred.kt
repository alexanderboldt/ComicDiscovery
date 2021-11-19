package com.alex.comicdiscovery.feature.character.starred.model

import com.alex.comicdiscovery.feature.base.UiEvent

sealed class UiEventCharacterStarred : UiEvent {
    object StarlistSettingsScreen : UiEventCharacterStarred()
    data class DetailScreen(val id: Int) : UiEventCharacterStarred()
}