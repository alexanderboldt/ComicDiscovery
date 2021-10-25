package com.alex.comicdiscovery.feature.character.overview.model

import com.alex.comicdiscovery.feature.base.UiEvent

sealed class UiEventCharacterOverview : UiEvent {
    data class DetailScreen(val id: Int) : UiEventCharacterOverview()
}