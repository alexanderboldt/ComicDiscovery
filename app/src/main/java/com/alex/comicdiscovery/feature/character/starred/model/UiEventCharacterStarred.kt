package com.alex.comicdiscovery.feature.character.starred.model

import com.alex.comicdiscovery.feature.base.UiEvent

sealed class UiEventCharacterStarred : UiEvent {
    data class DetailScreen(val id: Int) : UiEventCharacterStarred()
}